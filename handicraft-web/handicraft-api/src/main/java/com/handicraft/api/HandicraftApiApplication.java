package com.handicraft.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages={"com.handicraft.*"})
@EnableTransactionManagement
public class HandicraftApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HandicraftApiApplication.class, args);
	}
}
