package com.online.abaca.repository;

import com.online.abaca.model.CartHead;
import com.online.abaca.model.OrderHead;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHeadRepository extends JpaRepository<OrderHead, Long> {
}
