package com.redskt.classroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.redskt"})
public class RedClassApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedClassApplication.class, args);
    }
}
