package com.handicraft.admin.util;

import com.handicraft.core.dto.Authorities.Authority;
import com.handicraft.core.dto.Users.UserToAuthority;
import com.handicraft.core.service.Users.UserToAuthorityService;
import com.handicraft.core.support.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 고승빈 on 2017-06-29.
 */

@Slf4j
@Component
public class AuthenticationLoginSuccessAfter implements AuthenticationSuccessHandler{


    private UserToAuthorityService userToAuthorityService;

    @Value("${ADMIN.NAME}")
    private String name;

    @Value("${ADMIN.PHONE}")
    private String phone;

    @Value("${ADMIN.ADDRESS}")
    private String address;

    @Value("${server.session.timeout}")
    private String expired;

    @Autowired
    public AuthenticationLoginSuccessAfter(UserToAuthorityService userToAuthorityService) {
        this.userToAuthorityService = userToAuthorityService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        log.info("Login Success");

        UserToAuthority userToAuthority = userToAuthorityService.find(1);
        Authority authority;

        if(userToAuthority == null)
        {
            authority = new Authority();
            authority.setAid(0);
            authority.setAccountExpired(false);
            authority.setAccountLocked(false);
            authority.setCredentialsExpired(false);
            authority.setCredentialsLocked(false);
            authority.setPassword("1234");
            authority.setRole(Role.ADMIN);
            authority.setEnabled(true);

            userToAuthority = new UserToAuthority();
            userToAuthority.setUid(1);
            userToAuthority.setPhone(phone);
            userToAuthority.setAddress(address);
            userToAuthority.setJoinAt(null);
            userToAuthority.setName(name);
            userToAuthority.setAuthority(authority);

            userToAuthorityService.insert(userToAuthority);

            log.info("Admin Account Insert");

        }
        else
        {
            authority = userToAuthority.getAuthority();
            authority.setAccountExpired(false);
            authority.setAccountLocked(false);
            authority.setCredentialsExpired(false);
            authority.setCredentialsLocked(false);
            authority.setEnabled(true);
            userToAuthority.setAuthority(authority);
            userToAuthorityService.update(userToAuthority);
        }


        httpServletResponse.sendRedirect("/");

    }
}
