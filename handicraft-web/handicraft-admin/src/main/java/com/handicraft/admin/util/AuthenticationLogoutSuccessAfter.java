package com.handicraft.admin.util;

import com.handicraft.core.dto.Authorities.Authority;
import com.handicraft.core.dto.Users.UserToAuthority;
import com.handicraft.core.service.Users.UserToAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationLogoutSuccessAfter implements LogoutSuccessHandler {

    @Autowired
    private UserToAuthorityService userToAuthorityService;


    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        UserToAuthority userToAuthority = userToAuthorityService.find(1);

        Authority authority = userToAuthority.getAuthority();
        authority.setEnabled(false);

        userToAuthority.setAuthority(authority);

        userToAuthorityService.update(userToAuthority);

        httpServletResponse.sendRedirect("/");
    }
}
