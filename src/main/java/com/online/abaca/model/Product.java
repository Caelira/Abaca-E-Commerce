package com.online.abaca.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idproduct")
    private Long idProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idseller", nullable = false)
    private Seller seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcategory", nullable = false)
    private Category category;

    @Column(name = "product_name", length = 150, nullable = false)
    private String productName;

    @Column(name = "product_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal productPrice;

    @Column(name = "original_price", precision = 10, scale = 2)
    private BigDecimal originalPrice;

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "total_sold", nullable = false)
    private Integer totalSold;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    private boolean deleted = false;

    @Lob
    @Column(name = "product_image")
    private byte[] productImage;
}