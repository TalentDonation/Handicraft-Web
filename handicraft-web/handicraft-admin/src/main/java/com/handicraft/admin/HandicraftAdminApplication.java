package com.handicraft.admin;

import com.handicraft.admin.config.SiteMeshConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@SpringBootApplication(scanBasePackages={"com.handicraft.*"})
@EnableOAuth2Sso
public class HandicraftAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(HandicraftAdminApplication.class, args);
	}

}
