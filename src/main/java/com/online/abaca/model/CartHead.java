package com.online.abaca.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_head")
public class CartHead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_buyer", nullable = false)
    private Buyer buyer;

    @Column(name = "status", length = 50, nullable = false)
    private String status;

    public Long getIdCart() {
        return idCart;
    }

    public void setIdCart(Long idCart) {
        this.idCart = idCart;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
