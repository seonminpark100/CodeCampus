package com.lms.springboot;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<<<<<<< HEAD

import org.springframework.context.ApplicationContext;

=======
<<<<<<< HEAD
=======
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
>>>>>>> master
>>>>>>> origin/master

@SpringBootApplication
@MapperScan(basePackages = "com.lms.springboot.jdbc")
public class LmsProjectApplication {

<<<<<<< HEAD
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(LmsProjectApplication.class, args);
    
    }
}
=======
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
>>>>>>> origin/master
