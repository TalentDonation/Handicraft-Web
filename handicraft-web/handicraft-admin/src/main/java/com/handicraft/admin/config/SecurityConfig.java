package com.handicraft.admin.config;

import com.handicraft.admin.util.AuthenticationSuccessAfter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.savedrequest.RequestCacheAwareFilter;

import javax.servlet.Filter;


/**
 * Created by 고승빈 on 2017-06-28.
 */
@Configuration
@EnableOAuth2Client
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private OAuth2ClientContext oauth2ClientContext;

    @Bean
    @ConfigurationProperties("naver.client")
    protected AuthorizationCodeResourceDetails naverClientProperties() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties("naver.resource")
    protected ResourceServerProperties naverResourceProperties() {
        return new ResourceServerProperties();
    }

    private Filter oauth2Filter() {

        OAuth2ClientAuthenticationProcessingFilter oAuth2ClientAuthenticationProcessingFilter = new OAuth2ClientAuthenticationProcessingFilter("/calender/naverlogin");
        OAuth2RestTemplate naverTemplate = new OAuth2RestTemplate(naverClientProperties(), oauth2ClientContext);
        oAuth2ClientAuthenticationProcessingFilter.setRestTemplate(naverTemplate);

        UserInfoTokenServices tokenServices = new UserInfoTokenServices(naverResourceProperties().getUserInfoUri(), naverClientProperties().getClientId() );
        tokenServices.setRestTemplate(naverTemplate);
        oAuth2ClientAuthenticationProcessingFilter.setTokenServices(tokenServices);
        return oAuth2ClientAuthenticationProcessingFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .inMemoryAuthentication()
                    .withUser("admin")
                    .password("1234")
                    .roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http
                .authorizeRequests()

                    .antMatchers("/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                .and()

                .addFilterAfter( oauth2Filter(), RequestCacheAwareFilter.class)

                .formLogin()
                    .loginPage("/login")
                    .successHandler(new AuthenticationSuccessAfter())
                    .permitAll()
                .and()
                .logout()
                    .permitAll();



    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);

        web
                .ignoring()
                .antMatchers("/css/**" , "/js/**");
    }



}