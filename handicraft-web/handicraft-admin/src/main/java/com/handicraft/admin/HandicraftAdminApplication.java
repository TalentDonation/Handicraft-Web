package com.handicraft.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.handicraft.admin.filter.SiteMeshConfig;

@SpringBootApplication(scanBasePackages={"com.handicraft.*"})
public class HandicraftAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(HandicraftAdminApplication.class, args);
	}
	
	@Bean
	public FilterRegistrationBean siteMeshFilterRegisration()
	{
		FilterRegistrationBean filter = new FilterRegistrationBean();
		filter.setFilter(new SiteMeshConfig());
		
		return filter;
	}
}
