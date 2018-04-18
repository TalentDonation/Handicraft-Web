package com.handicraft.api.security;

import com.handicraft.api.exception.NotAcceptableException;
import com.handicraft.core.support.AESUtil;
import com.handicraft.core.domain.User;
import com.handicraft.core.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.time.ZonedDateTime;

@Slf4j
public class SecurityProvider implements AuthenticationProvider {
    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getCredentials();
        User user = userService.loadUserByUsername(token);
        if (user == null || !user.isEnabled() || user.getToken() == null) {
            throw new NotAcceptableException("Problem Tocken. Check Please Tocken.");
        }

        String decryptedToken = null;
        try {
            decryptedToken = AESUtil.decrypt(user.getToken(), user.getSecretKey());
        } catch (Exception e) {
            log.error("When Decription Tocken, Gennerate Error : {} ", e.getMessage());
        }

        String expired = StringUtils.substringAfter(decryptedToken, "/");
        if (ZonedDateTime.now().compareTo(ZonedDateTime.parse(expired)) > 0) {
            user.modifyAuthStatus(true, true, false);
            userService.update(user);
            throw new NotAcceptableException("Expired Token");
        }

        SecurityAuthentication securityAuthentication = new SecurityAuthentication(token, user);
        securityAuthentication.setAuthenticated(true);
        return securityAuthentication;
    }

    /*
    * authenticate 의 리턴값의 반환형을 확인
    * */
    @Override
    public boolean supports(Class<?> authenticationClass) {
        return SecurityAuthentication.class.isAssignableFrom(authenticationClass);
    }
}
