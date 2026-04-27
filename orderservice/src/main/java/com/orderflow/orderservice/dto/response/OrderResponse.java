package com.orderflow.orderservice.dto.response;

import com.orderflow.orderservice.entity.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Response body after successful order creation")
public class OrderResponse {

    @Schema(description = "Auto-generated order ID", example = "1")
    private Long id;
    @Schema(description = "Customer name", example = "Aryan Tomar")
    private String customerName;
    @Schema(description = "Product name", example = "Mechanical Keyboard")
    private String productName;
    @Schema(description = "Quantity ordered", example = "2")
    private Integer quantity;
    @Schema(description = "Price per unit", example = "4999.99")
    private BigDecimal price;
    @Schema(description = "Current order status", example = "CREATED")
    private OrderStatus status;
    @Schema(description = "Order creation timestamp")
    private LocalDateTime createdAt;

}
