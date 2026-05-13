package com.online.abaca.dto;

import lombok.Data;

@Data
public class SellerResponseDTO {
    private Long idSeller;
    private Long idUser;
    private String contactNumber;
    private String storeName;
}