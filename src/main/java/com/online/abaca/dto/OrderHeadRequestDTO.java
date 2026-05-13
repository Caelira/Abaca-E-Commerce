package com.online.abaca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderHeadRequestDTO {

    @NotNull(message = "Buyer ID is required")
    private Long idBuyer;

    private Long idCart;

    @NotNull(message = "Shipping Address ID is required")
    private Long idShippingAddress;

    @NotBlank(message = "Order status is required")
    private String orderStatus;
}