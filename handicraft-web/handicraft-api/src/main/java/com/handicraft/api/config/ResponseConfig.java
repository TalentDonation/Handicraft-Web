package com.handicraft.api.config;

import com.handicraft.api.config.filter.ResponseFileter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResponseConfig {

    @Bean
    public ResponseFileter responseFileter() {
        return new ResponseFileter();
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean result = new FilterRegistrationBean();
        result.setFilter(responseFileter());
        result.addUrlPatterns("*");
        result.setName("responseFilter");
        return result;
    }
}
