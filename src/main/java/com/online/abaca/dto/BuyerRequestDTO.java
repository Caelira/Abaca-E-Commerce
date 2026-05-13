package com.online.abaca.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BuyerRequestDTO {

    @NotNull(message = "User ID is required")
    private Long idUser;

    private String contactNumber;
}