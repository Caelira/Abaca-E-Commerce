package com.online.abaca.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OrderHeadResponseDTO {
    private Long idOrder;
    private Long idBuyer;
    private Long idCart;
    private Long idShippingAddress;
    private LocalDateTime orderDate;
    private String orderStatus;
}