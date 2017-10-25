package com.handicraft.admin.controller;

import com.handicraft.core.dto.Authorities.Authority;
import com.handicraft.core.dto.Users.UserToAuthority;
import com.handicraft.core.service.Users.UserToAuthorityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by 고승빈 on 2017-06-28.
 */

@Controller
@Slf4j
public class AuthController {

    @Autowired
    private UserToAuthorityService userToAuthorityService;


    @GetMapping("/login")
    public ModelAndView login()
    {
        log.info("Login connect to form");


        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");


        return mv;
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest httpServletRequest , HttpServletResponse httpServletResponse) throws IOException {
        log.info("Logout");
        UserToAuthority userToAuthority = userToAuthorityService.find(1);

        Authority authority = userToAuthority.getAuthority();
        authority.setEnabled(false);

        userToAuthority.setAuthority(authority);

        userToAuthorityService.update(userToAuthority);

        httpServletRequest.getSession(false).invalidate();

        httpServletResponse.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);

        httpServletResponse.sendRedirect("/");
    }

}

