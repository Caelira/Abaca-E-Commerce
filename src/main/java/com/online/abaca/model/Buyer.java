package com.online.abaca.model;

import jakarta.persistence.*;

@Entity
public class Buyer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idbuyer")
    private Long idBuyer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iduser", nullable = false, unique = true)
    private UserAccount userAccount;

    @Column(name = "contact_number")
    private String contactNumber;

    public Long getIdBuyer() {
        return idBuyer;
    }

    public void setIdBuyer(Long idBuyer) {
        this.idBuyer = idBuyer;
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
}
