package com.online.abaca.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserAccountResponseDTO {
    private Long idUser;
    private String email;
    private String role;
    private byte[] userProfile;
    private LocalDateTime createdAt;
}