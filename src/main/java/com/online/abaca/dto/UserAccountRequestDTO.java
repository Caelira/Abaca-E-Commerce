package com.online.abaca.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserAccountRequestDTO {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    private byte[] userProfile;
}