package com.orderflow.orderservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
@Tag(name = "Health", description = "Service health check")
public class HealthCheckController {

    @GetMapping()
    @Operation(summary = "Health check", description = "Returns service status")
    public String healthCheck(){
        return "Order service is UP!!!";
    }
}
