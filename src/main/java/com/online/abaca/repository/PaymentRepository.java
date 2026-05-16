package com.online.abaca.repository;

import com.online.abaca.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrderHead_IdOrder(Long idOrder);
}
