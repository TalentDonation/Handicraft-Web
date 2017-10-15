package com.handicraft.api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.handicraft.api.exception.UnAuthorizedException;
import com.handicraft.api.utils.EncrypttionUtil;
import com.handicraft.core.dto.*;
import com.handicraft.core.service.UserService;
import com.handicraft.core.service.UserToAuthorityService;
import com.handicraft.core.service.UserToImageService;
import com.handicraft.core.utils.enums.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class AuthController {


    @Autowired
    UserService userService;

    @Autowired
    UserToImageService userToImageService;

    @Autowired
    UserToAuthorityService userToAuthorityService;

    @Value("${images-path}")
    String imagesPath;

    @PostMapping(value = "/auth/signin")
    @Transactional
    public  ResponseEntity signin(@RequestParam("access_token") String access_token) throws NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {

        log.info(access_token);

        ResponseEntity naverAuthentication = authenticateNaver(access_token);

        JsonParser jsonParser = JsonParserFactory.getJsonParser();
        Map<String , Object> result = jsonParser.parseMap(naverAuthentication.getBody().toString());

        log.info(naverAuthentication.getBody().toString());

        Map<String , Object> responseMap = (HashMap<String,Object>) result.get("response");

        User user = userService.findByUser(Integer.parseInt(responseMap.get("id").toString()));


        if(user == null) throw new UnAuthorizedException();

        UserToAuthority userToAuthority = userToAuthorityService.find(user.getUid());
        userToAuthority.setJoinAt(null);
        Authority authority = userToAuthority.getAuthority();
        authority.setEnabled(true);
        userToAuthority.setAuthority(authority);
        UserToAuthority LoginedUser = userToAuthorityService.update(userToAuthority);

        MultiValueMap<String ,String> headers = new HttpHeaders();



        headers.add("Authorization" , "craft " + EncrypttionUtil.AES_Encrypt(LoginedUser));



        return new ResponseEntity(headers, HttpStatus.OK );
    }


    @PostMapping("/auth/signup")
    @Transactional
    public ResponseEntity signup(@RequestParam("access_token") String access_token , @ModelAttribute User user, MultipartFile multipartFile) throws NoSuchPaddingException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {

        log.info(access_token);

        ResponseEntity naverAuthentication = authenticateNaver(access_token);

        JsonParser jsonParser = JsonParserFactory.getJsonParser();
        Map<String , Object> result = jsonParser.parseMap(naverAuthentication.getBody().toString());

        log.info(naverAuthentication.getBody().toString());

        Map<String , Object> responseMap = (HashMap<String,Object>) result.get("response");

        UserToImage userToImage = new UserToImage(user);
        userToImage.setUid(Integer.parseInt(responseMap.get("id").toString()));
        UserToImage insertResult;
        UserToAuthority userToAuthority , updateResult;
        Authority authority;

        if(multipartFile != null)
        {
            Image image = new Image();
            image.setGid(0);
            image.setName(imagesPath);
            image.setExtension(StringUtils.getFilenameExtension(multipartFile.getOriginalFilename()));

            userToImage.setImage(image);

            log.info(userToImage.toString());
            log.info(userToImage.getImage().toString());


            insertResult = userToImageService.insertToUserToImage(userToImage);

            log.info("insert : " + insertResult.toString());

            userToAuthority = new UserToAuthority(insertResult);
            authority = new Authority();

            authority.setAid(0);
            authority.setRole(Role.USER);
            authority.setAccountExpired(false);
            authority.setAccountLocked(false);
            authority.setCredentialsExpired(false);
            authority.setCredentialsLocked(false);
            authority.setPassword("NOTHING");
            authority.setEnabled(true);

            userToAuthority.setAuthority(authority);

            log.info("update : " + userToAuthority.getUid());

            updateResult = userToAuthorityService.update(userToAuthority);

            File file;
            StringBuffer uri = new StringBuffer();

            uri.append(ResourceUtils.getFile("classpath:static/images").getPath())
                    .append("/").append(insertResult.getImage().getGid())
                    .append(".").append(insertResult.getImage().getExtension());
            file = new File(uri.toString());
            multipartFile.transferTo(file);


        }
        else
        {
            insertResult = userToImageService.insertToUserToImage(userToImage);
            userToAuthority = new UserToAuthority(insertResult);
            authority = new Authority();

            authority.setAid(0);
            authority.setRole(Role.USER);
            authority.setAccountExpired(false);
            authority.setAccountLocked(false);
            authority.setCredentialsExpired(false);
            authority.setCredentialsLocked(false);
            authority.setPassword("NOTHING");
            authority.setEnabled(true);

            userToAuthority.setAuthority(authority);
            updateResult = userToAuthorityService.update(userToAuthority);
        }

        MultiValueMap<String ,String> headers = new HttpHeaders();


        headers.add("Authorization" , "craft " + EncrypttionUtil.AES_Encrypt(updateResult));


        return new ResponseEntity(headers, HttpStatus.OK );
    }


    private ResponseEntity authenticateNaver(String access_token)
    {
        RestTemplate restTemplate = new RestTemplate();

        URI uri = URI.create("https://openapi.naver.com/v1/nid/me");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization","Bearer "+access_token);

        RequestEntity requestEntity = new RequestEntity(httpHeaders, HttpMethod.POST, uri);

        ResponseEntity<String> responseEntity = null;


        responseEntity = restTemplate.exchange(requestEntity,String.class);

        return responseEntity;
    }
}
