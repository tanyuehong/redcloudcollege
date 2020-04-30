package com.redskt.collegeservice.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.redskt.collegeservice.mapper")
public class CollegeConfig {
}
