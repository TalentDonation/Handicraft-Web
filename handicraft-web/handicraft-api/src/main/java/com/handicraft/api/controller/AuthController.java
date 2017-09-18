package com.handicraft.api.controller;


import com.handicraft.api.exception.UnAuthorizedException;
import com.handicraft.api.utils.EncrypttionUtil;
import com.handicraft.core.dto.Image;
import com.handicraft.core.dto.User;
import com.handicraft.core.dto.UserToImage;
import com.handicraft.core.service.UserService;
import com.handicraft.core.service.UserToImageService;
import com.handicraft.core.utils.enums.Gender;
import io.swagger.annotations.ApiImplicitParam;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class AuthController {


    @Autowired
    UserService userService;

    @Autowired
    UserToImageService userToImageService;

    @Value("${images-path}")
    String imagesPath;

    @PostMapping("/auth/signin")
    @Transactional
    public  ResponseEntity signin(@RequestParam("access_token") String access_token)
    {

        ResponseEntity naverAuthentication = authenticateNaver(access_token);

        JsonParser jsonParser = JsonParserFactory.getJsonParser();
        Map<String , Object> result = jsonParser.parseMap(naverAuthentication.getBody().toString());

        log.info(naverAuthentication.getBody().toString());

        Map<String , Object> responseMap = (HashMap<String,Object>) result.get("response");

        User user = userService.findByUser(Integer.parseInt(responseMap.get("id").toString()));


        if(user == null) throw new UnAuthorizedException();


        MultiValueMap<String ,String> headers = new HttpHeaders();


        try {
            headers.add("Authorization" , "craft " + EncrypttionUtil.AES_Encrypt(user));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }


        return new ResponseEntity(headers, HttpStatus.OK );
    }


    @PostMapping("/auth/signup")
    @Transactional
    public ResponseEntity signup(@RequestParam("access_token") String access_token , @ModelAttribute("user") User user , MultipartFile multipartFile)
    {
        ResponseEntity naverAuthentication = authenticateNaver(access_token);

        JsonParser jsonParser = JsonParserFactory.getJsonParser();
        Map<String , Object> result = jsonParser.parseMap(naverAuthentication.getBody().toString());

        log.info(naverAuthentication.getBody().toString());

        Map<String , Object> responseMap = (HashMap<String,Object>) result.get("response");

        UserToImage userToImage = new UserToImage(user);
        userToImage.setUid(Integer.parseInt(responseMap.get("id").toString()));

        Image image = new Image();
        image.setGid(0);
        image.setName(imagesPath);
        image.setExtension(multipartFile.getOriginalFilename().split("\\.")[1]);

        userToImage.setImage(image);

        log.info(userToImage.toString());
        log.info(userToImage.getImage().toString());

        UserToImage insertResult = userToImageService.insertToUserToImage(userToImage);

        Resource resource = new ClassPathResource("static/images");
        File file ;
        StringBuffer uri = new StringBuffer();

        try {

            uri.append(resource.getFile().toString())
                    .append("/").append(insertResult.getImage().getGid())
                    .append(".").append(insertResult.getImage().getExtension());
            file = new File(uri.toString());
            multipartFile.transferTo(file);

        } catch (IOException e) {
            e.printStackTrace();
        }

        MultiValueMap<String ,String> headers = new HttpHeaders();

        try {

            headers.add("Authorization" , "craft " + EncrypttionUtil.AES_Encrypt(insertResult));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

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
