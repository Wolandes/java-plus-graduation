package ru.practicum.requestservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "ru.practicum.api.client")
@SpringBootApplication(scanBasePackages = {"ru.practicum.requestservice", "ewm.client"})public class RequestService {
    public static void main(String[] args) {
        SpringApplication.run(RequestService.class, args);
    }
}