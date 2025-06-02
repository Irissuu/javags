package com.example.demo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info =
@Info(title = "API FlowGuard", description = "FlowGuard", version = "v.01"))
public class ApiRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiRestApplication.class, args);
    }

}

