package com.hloong;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hloong.project.mapper")
@EnableDubbo
public class LoongApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoongApiApplication.class, args);
    }

}
