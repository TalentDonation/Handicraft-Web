package com.handicraft.admin.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by 고승빈 on 2017-06-29.
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean siteMeshFilterRegisration()
    {
        FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(new SiteMeshConfig());

        return filter;
    }

}
