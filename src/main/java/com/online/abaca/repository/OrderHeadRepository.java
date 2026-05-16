package com.online.abaca.repository;

import com.online.abaca.model.CartHead;
import com.online.abaca.model.OrderHead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderHeadRepository extends JpaRepository<OrderHead, Long> {
    List<OrderHead> findByBuyer_IdBuyerOrderByOrderDateDesc(Long idBuyer);
}
