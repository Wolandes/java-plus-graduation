package ru.practicum.compilationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "ru.practicum.api.client")
@SpringBootApplication
public class CompilationService {
    public static void main(String[] args) {
        SpringApplication.run(CompilationService.class, args);
    }
}