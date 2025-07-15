package com.lms.springboot;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
//@MapperScan(basePackages = "com.lms.springboot.jdbc")
public class LmsProjectApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(LmsProjectApplication.class, args);
    
    }
}