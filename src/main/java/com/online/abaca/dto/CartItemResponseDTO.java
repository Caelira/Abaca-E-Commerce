package com.online.abaca.dto;

import lombok.Data;

@Data
public class CartItemResponseDTO {
    private Long idCartItem;
    private Long idCart;
    private Long idProduct;
    private Integer quantity;
}