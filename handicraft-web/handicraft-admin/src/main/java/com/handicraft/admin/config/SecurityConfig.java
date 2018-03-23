package com.handicraft.admin.config;

import com.handicraft.admin.util.AuthenticationLoginSuccessAfter;
import com.handicraft.core.support.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


/**
 * Created by 고승빈 on 2017-06-28.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationLoginSuccessAfter authenticationLoginSuccessAfter;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .inMemoryAuthentication()
                    .withUser("admin")
                    .password("1234")
                    .roles(Role.ADMIN.toString());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http
                .csrf()
                .and()
                .authorizeRequests()

                    .antMatchers("/**").hasRole(Role.ADMIN.toString())
                    .anyRequest().authenticated()
                .and()
                .exceptionHandling().accessDeniedPage("/error401.jsp")
                .and()

                .formLogin()
                    .loginPage("/login")
                    .successHandler(authenticationLoginSuccessAfter)
                    .permitAll()
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    ;



    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);

        web
                .ignoring()
                .antMatchers("/css/**" , "/js/**");
    }




}