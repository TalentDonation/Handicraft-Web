package com.handicraft.api.config;


import com.handicraft.api.Security.SecurityAuthenticationFailure;
import com.handicraft.api.Security.SecurityProvider;
import com.handicraft.api.Security.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    SecurityProvider securityProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AuthenticationFailureHandler authenticationFailureHandler;

    /*
    * provider를 통한 인증
    * */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(securityProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http

                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(getSecurityFilter() , UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                    .antMatchers("/auth/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint())
                ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**","/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/**", "/swagger-resources/configuration/ui", "/swagge‌​r-ui.html", "/swagger-resources/configuration/security");
    }

    private RequestMatcher getRequestMatcher() {

        List<RequestMatcher> requestMatchers = new ArrayList<>();

        requestMatchers.add(new AntPathRequestMatcher("/users/**"));
        requestMatchers.add(new AntPathRequestMatcher("/furniture/**"));


        return new OrRequestMatcher(requestMatchers);
    }

    private SecurityFilter getSecurityFilter() throws Exception {
        SecurityFilter filter = new SecurityFilter(getRequestMatcher());
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationFailureHandler(authenticationFailureHandler);

        return filter;
    }


}
