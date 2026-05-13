package com.online.abaca.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductResponseDTO {
    private Long idProduct;
    private Long idSeller;
    private Long idCategory;
    private String productName;
    private BigDecimal productPrice;
    private Integer stockQuantity;
}
