package com.luna.springbootpractice;

import com.luna.springbootpractice.demo.myfegin.EnableMyFeginClientAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMyFeginClientAutoConfiguration(basePackage = "com.luna.springbootpractice.demo.service")
public class SpringbootpracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootpracticeApplication.class, args);
    }

}
