package com.emma.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.emma.blog.mapper")
public class BlogMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogMybatisApplication.class, args);
    }

}
