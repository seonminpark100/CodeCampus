package com.lms.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<<<<<<< HEAD
=======
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
>>>>>>> master

@SpringBootApplication
public class LmsProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(LmsProjectApplication.class, args);
<<<<<<< HEAD
	}

=======
		
//		String passwd = 
//				PasswordEncoderFactories.createDelegatingPasswordEncoder()
//				.encode("1234");
//		System.out.println(passwd);
	}
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
>>>>>>> master
}
