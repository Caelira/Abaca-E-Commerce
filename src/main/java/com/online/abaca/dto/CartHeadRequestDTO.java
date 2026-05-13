package com.online.abaca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartHeadRequestDTO {

    @NotNull(message = "Buyer ID is required")
    private Long idBuyer;

    @NotBlank(message = "Status is required")
    private String status;
}