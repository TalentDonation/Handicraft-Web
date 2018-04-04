package com.handicraft.api.service;

import com.handicraft.core.support.AESUtil;
import com.handicraft.core.domain.User;
import com.handicraft.core.repository.UserRepository;
import com.handicraft.core.support.Role;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class AuthService {
    private static final URI URI_INFO = URI.create("https://openapi.naver.com/v1/nid/me");

    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<Map<String, String>> findProfile(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        RequestEntity requestEntity = RequestEntity.post(URI_INFO).header("Authorization", "Bearer " + accessToken).build();
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            JsonParser JSON_PARSER = JsonParserFactory.getJsonParser();
            Map<String, Object> result = JSON_PARSER.parseMap(responseEntity.getBody());
            Map<String, String> profile = Stream.of(StringUtils.substringBetween(result.get("response").toString(), "{", "}").split(","))
                    .map(it -> it = it.trim())
                    .collect(Collectors.toMap(it -> it.split("=")[0], it -> it.split("=")[1]));

            return Optional.of(profile);
        }

        return Optional.empty();
    }

    public Optional<String> createToken(long uid) {
        Optional<User> checkedUser = initLogin(uid);
        Optional result = Optional.empty();
        if (checkedUser.isPresent()) {
            try {
                String secretKey = AESUtil.genererateRandomKey();
                String token = AESUtil.encrypt(checkedUser.get(), secretKey);
                User user = checkedUser.get();
                user.setRole(Role.USER.name());
                user.modifyAuthToken(token, secretKey);
                userRepository.save(user);
                result = Optional.of("craft " + token);
            } catch (Exception e) {
                log.error("암호화 과정에서 에러가 발생했습니다. : {}", e.getMessage());
            }
        }

        return result;
    }

    private Optional<User> initLogin(long uid) {
        User user = userRepository.findOne(uid);
        if (user == null) {
            return Optional.empty();
        }

        user.modifyAuthStatus(false, false, true);
        return Optional.of(userRepository.save(user));
    }
}
