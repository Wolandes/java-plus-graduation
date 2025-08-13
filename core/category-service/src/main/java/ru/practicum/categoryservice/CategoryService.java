package ru.practicum.categoryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "ru.practicum.api.client")
@SpringBootApplication
public class CategoryService {
    public static void main(String[] args) {
        SpringApplication.run(CategoryService.class, args);
    }
}