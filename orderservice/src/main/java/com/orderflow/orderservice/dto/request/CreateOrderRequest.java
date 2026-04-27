package com.orderflow.orderservice.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request body for creating a new order")
public class CreateOrderRequest {

    @NotBlank(message = "Customer name is required")
    @Schema(
            description = "Full name of the customer",
            example = "Aryan Tomar"
    )
    private String customerName;
    @NotBlank(message = "Product name is required")
    @Schema(
            description = "Name of the product being ordered",
            example = "Mechanical Keyboard"
    )
    private String productName;
    @NotNull(message = "Quantity is required")
    @Min(value=1, message = "Quantity must be at-least 1")
    @Schema(
            description = "Number of units ordered",
            example = "2",
            minimum = "1"
    )
    private Integer quantity;
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    @Schema(
            description = "Price per unit in INR",
            example = "4999.99"
    )
    private BigDecimal price;
}
