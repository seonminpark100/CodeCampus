package com.lms.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment; // 추가

@SpringBootApplication
public class LmsProjectApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(LmsProjectApplication.class, args);

        // 설정 값 확인 (테스트용)
        Environment env = context.getEnvironment();
        String maxParams = env.getProperty("server.tomcat.max-http-form-post-parameters");
        System.out.println("DEBUG: server.tomcat.max-http-form-post-parameters = " + maxParams);

        // 다른 멀티파트 설정도 확인 가능
        String multipartEnabled = env.getProperty("jakarta.servlet.multipart.enabled");
        System.out.println("DEBUG: jakarta.servlet.multipart.enabled = " + multipartEnabled);
        String maxFileSize = env.getProperty("jakarta.servlet.multipart.max-file-size");
        System.out.println("DEBUG: jakarta.servlet.multipart.max-file-size = " + maxFileSize);
    }
}