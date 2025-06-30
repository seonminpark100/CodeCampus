package com.lms.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

@SpringBootApplication
public class LmsProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(LmsProjectApplication.class, args);
		
	}

}
