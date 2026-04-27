package com.orderflow.orderservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI orderServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Order Service API")
                        .description(
                                "Event-Driven Order Processing System — Order Service. " +
                                        "Creates orders, persists to PostgreSQL, and publishes " +
                                        "OrderCreatedEvent to Kafka for downstream services."
                        )
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Aryan Tomar")
                                .url("https://github.com/Aryan-Singh-Tomar"))
                        .license(new License()
                                .name("MIT License"))
                );
    }
}