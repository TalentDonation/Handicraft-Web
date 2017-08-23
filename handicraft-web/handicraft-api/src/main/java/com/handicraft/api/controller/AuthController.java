package com.handicraft.api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.handicraft.api.exception.BadRequestException;
import com.handicraft.api.utils.EncrypttionUtil;
import com.handicraft.core.dto.User;
import com.handicraft.core.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SimpleFormatter;

@RestController
public class AuthController {

    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    UserService userService;

    @PostMapping("/auth/login")
    public ResponseEntity login(@RequestParam("access_token") String access_token)
    {
        RestTemplate restTemplate = new RestTemplate();

        URI uri = URI.create("https://openapi.naver.com/v1/nid/me");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization","Bearer "+access_token);

        RequestEntity requestEntity = new RequestEntity(httpHeaders, HttpMethod.POST, uri);

        ResponseEntity<String> responseEntity = null;


        responseEntity = restTemplate.exchange(requestEntity,String.class);


        JsonParser jsonParser = JsonParserFactory.getJsonParser();
        Map<String , Object> result = jsonParser.parseMap(responseEntity.getBody());

        logger.info(responseEntity.getBody());
        logger.info(result.get("resultcode").toString());
        logger.info(result.get("message").toString());
        logger.info(result.get("response").toString());

        Map<String , Object> responseMap = (HashMap<String,Object>) result.get("response");


        User user = userService.findByUser(Integer.parseInt(responseMap.get("id").toString()));


        if(user == null)
        {
            SimpleDateFormat simpleFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currentDate = new Date();
            user = new User();
            user.setUid(userService.findLastUser().getUid() + 1);
            user.setGender(responseMap.get("gender").toString().equals("M") ? true : false);
            user.setName(responseMap.get("name").toString());
            user.setJoinAt(simpleFormatter.format(currentDate));

            User userForInfo = userService.insertToUser(user);

            logger.info("User Temp Insert : " + userForInfo);
        }

        MultiValueMap<String ,String> headers = new HttpHeaders();
        try {
            headers.add("token" , EncrypttionUtil.AES_Encrypt(user));
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
}
