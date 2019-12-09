package com.stormer.authenservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.stormer")
@Slf4j
public class AuthenServiceApplication {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    public static void main(String[] args) {

        SpringApplication.run(AuthenServiceApplication.class, args);
    }

    @Bean
    public void logg() {
        log.info("============DataSource: {}", datasourceUrl );
    }

}
