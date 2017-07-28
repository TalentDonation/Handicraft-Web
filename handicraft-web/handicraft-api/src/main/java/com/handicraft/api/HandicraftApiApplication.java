package com.handicraft.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.handicraft.*"})
public class HandicraftApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HandicraftApiApplication.class, args);
	}
}
