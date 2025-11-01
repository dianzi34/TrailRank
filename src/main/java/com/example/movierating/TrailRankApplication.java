package com.example.movierating;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.movierating.db.mappers")
public class TrailRankApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrailRankApplication.class, args);
    }

}
