package com.online.abaca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SellerRequestDTO {

    @NotNull(message = "User ID is required")
    private Long idUser;

    @NotBlank(message = "Contact number is required")
    private String contactNumber;

    @NotBlank(message = "Store name is required")
    private String storeName;
}
