package com.online.abaca.model;

import jakarta.persistence.*;

@Entity
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idseller")
    private Long idSeller;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iduser", nullable = false, unique = true)
    private UserAccount userAccount;

    @Column(name = "contact_number", length = 15, nullable = false)
    private String contactNumber;

    @Column(name = "store_name", length = 100, nullable = false)
    private String storeName;

    public Long getIdSeller() {
        return idSeller;
    }

    public void setIdSeller(Long idSeller) {
        this.idSeller = idSeller;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
