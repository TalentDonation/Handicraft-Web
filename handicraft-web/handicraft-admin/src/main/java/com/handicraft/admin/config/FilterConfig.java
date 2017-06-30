package com.handicraft.admin.config;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.Ordered;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

/**
 * Created by 고승빈 on 2017-06-29.
 */
@Configuration
@EnableOAuth2Client
public class FilterConfig {

    @Bean
    public FilterRegistrationBean siteMeshFilterRegisration()
    {
        FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(new SiteMeshConfig());

        return filter;
    }

    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration( OAuth2ClientContextFilter filter)
    {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }



}
