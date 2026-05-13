package com.online.abaca.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddressRequestDTO {

    @NotNull(message = "User ID is required")
    private Long idUser;

    @NotBlank(message = "Address type is required")
    private String addressType;

    @NotBlank(message = "Street is required")
    @Size(max = 50, message = "Street must not exceed 50 characters")
    private String street;

    @NotBlank(message = "Barangay is required")
    @Size(max = 50, message = "Barangay must not exceed 50 characters")
    private String brgy;

    @NotBlank(message = "Municipality is required")
    @Size(max = 50, message = "Municipality must not exceed 50 characters")
    private String municipality;

    private String province = "Albay";
}
