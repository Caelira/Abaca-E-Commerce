package com.online.abaca.repository;

import com.online.abaca.model.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {
    Optional<Buyer> findByUserAccount_IdUser(Long idUser);
}

