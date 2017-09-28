package com.handicraft.api.Security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityFilter extends AbstractAuthenticationProcessingFilter {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public SecurityFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    /*
    * AuthenticationManager 인증 요청
    *
    * */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        String token = httpServletRequest.getHeader("authorization");

        if(!StringUtils.isEmpty(token)) {

            token = token.substring(6);

            logger.info("authorization : " + token);

            SecurityAuthentication securityAuthentication = new SecurityAuthentication(token);

            return getAuthenticationManager().authenticate(securityAuthentication);

        } else {
            throw new AccessDeniedException("Not empty Token");
        }
    }

    /*
    * 인증 성공
    * 인가를 위해 SecurityContext에 인증을 삽입
    * */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);

        logger.info("Authentication Success : " + authResult);
        chain.doFilter(request, response);
    }

    /*
    * 인증 실패
    *
    * */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        failed.printStackTrace();

        getFailureHandler().onAuthenticationFailure(request, response, failed);
    }



}
