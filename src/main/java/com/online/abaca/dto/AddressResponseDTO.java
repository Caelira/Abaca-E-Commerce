package com.online.abaca.dto;

import lombok.Data;

@Data
public class AddressResponseDTO {
    private Long idAddress;
    private Long idUser;
    private String addressType;
    private String street;
    private String brgy;
    private String municipality;
    private String province;
}
