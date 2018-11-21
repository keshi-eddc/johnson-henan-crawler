package com.eddc.johnsonhenan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.eddc.johnsonhenan.mapper")
public class JohnsonHenanApplication {

    public static void main(String[] args) {
        SpringApplication.run(JohnsonHenanApplication.class, args);
    }
}
