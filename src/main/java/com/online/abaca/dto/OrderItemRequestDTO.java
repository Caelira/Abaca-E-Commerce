package com.online.abaca.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderItemRequestDTO {

    @NotNull(message = "Order ID is required")
    private Long idOrder;

    @NotNull(message = "Product ID is required")
    private Long idProduct;

    @NotNull(message = "Order quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer orderQuantity;

    @NotNull(message = "Final unit price is required")
    @PositiveOrZero(message = "Price cannot be negative")
    private BigDecimal finalUnitPrice;
}