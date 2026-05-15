package com.online.abaca.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductResponseDTO {
    private Long idProduct;
    private Long idSeller;
    private Long idCategory;
    private String productName;
    private BigDecimal productPrice;
    private BigDecimal originalPrice;
    private Integer stockQuantity;
    private LocalDateTime createdAt;
    private Integer totalSold;
    private byte[] productImage;
}