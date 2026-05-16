package com.online.abaca.repository;

import com.online.abaca.model.CartHead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartHeadRepository extends JpaRepository<CartHead, Long> {
    Optional<CartHead> findByBuyer_IdBuyerAndStatus(Long idBuyer, String status);
}
