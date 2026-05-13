package com.online.abaca.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderItemResponseDTO {
    private Long idOrderItem;
    private Long idOrder;
    private Long idProduct;
    private Integer orderQuantity;
    private BigDecimal finalUnitPrice;
}