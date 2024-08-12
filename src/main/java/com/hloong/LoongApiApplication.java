package com.hloong;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hloong.api.mapper")
public class LoongApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoongApiApplication.class, args);
    }

}
