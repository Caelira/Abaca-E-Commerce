package com.online.abaca.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idorder_item")
    private Long idOrderItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idorder", nullable = false)
    private OrderHead orderHead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idproduct", nullable = false)
    private Product product;

    @Column(name = "order_quantity", nullable = false)
    private Integer orderQuantity;

    @Column(name = "final_unit_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal finalUnitPrice;

    public Long getIdOrderItem() {
        return idOrderItem;
    }

    public void setIdOrderItem(Long idOrderItem) {
        this.idOrderItem = idOrderItem;
    }

    public OrderHead getOrderHead() {
        return orderHead;
    }

    public void setOrderHead(OrderHead orderHead) {
        this.orderHead = orderHead;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(Integer orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public BigDecimal getFinalUnitPrice() {
        return finalUnitPrice;
    }

    public void setFinalUnitPrice(BigDecimal finalUnitPrice) {
        this.finalUnitPrice = finalUnitPrice;
    }
}
