package com.example.adminsystem.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.example.adminsystem.mapper") // Scan for mappers in this package
public class MybatisPlusConfig {
    // Basic configuration is often handled by spring-boot-starter-parent
    // and mybatis-plus-boot-starter.
    // You can add beans for PaginationInterceptor, OptimisticLockerInnerInterceptor, etc. here if needed later.
}
