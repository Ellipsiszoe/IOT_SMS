package com.example.iotdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.iotdemo.mapper")
public class IotdemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(IotdemoApplication.class, args);
    }

}
