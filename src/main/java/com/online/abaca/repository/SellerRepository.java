package com.online.abaca.repository;

import com.online.abaca.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    Optional<Seller> findByUserAccount_IdUser(Long idUser);
    List<Seller> findByStoreNameContainingIgnoreCase(String storeName);
    }
