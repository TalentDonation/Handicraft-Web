package com.handicraft.api.controller;


import com.handicraft.api.service.AuthService;
import com.handicraft.core.dto.UserDto;
import com.handicraft.core.dto.UserTokenDto;
import com.handicraft.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public AuthController(UserService userService, AuthService authService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping(value = "/auth/signin", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity signin(@RequestBody String accessToken) {
        Optional<Map<String, String>> profile = authService.findProfile(accessToken);
        if (profile.isPresent()) {
            Optional<String> token = authService.createToken(Long.parseLong(profile.get().get("id")));
            if (token.isPresent()) {
                return ResponseEntity.ok()
                        .header("Authorization", token.get())
                        .build();
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    @PostMapping(value = "/auth/signup", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity signup(@RequestBody UserTokenDto userTokenDto) {
        Optional<Map<String, String>> profile = authService.findProfile(userTokenDto.getAccessToken());
        UserDto userDto = userTokenDto.getUserDto();
        if (profile.isPresent()) {
            userDto.setUid(Long.parseLong(profile.get().get("id")));
            userService.create(userDto);
            Optional<String> token = authService.createToken(Long.parseLong(profile.get().get("id")));
            if (token.isPresent()) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .header("Authorization", token.get())
                        .build();
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
