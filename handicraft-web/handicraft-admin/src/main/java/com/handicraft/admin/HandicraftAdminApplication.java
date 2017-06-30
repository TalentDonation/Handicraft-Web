package com.handicraft.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages={"com.handicraft.*"})
public class HandicraftAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(HandicraftAdminApplication.class, args);
	}

}
