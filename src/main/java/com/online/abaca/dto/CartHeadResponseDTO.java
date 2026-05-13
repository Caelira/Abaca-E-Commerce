package com.online.abaca.dto;

import lombok.Data;

@Data
public class CartHeadResponseDTO {
    private Long idCart;
    private Long idBuyer;
    private String status;
}