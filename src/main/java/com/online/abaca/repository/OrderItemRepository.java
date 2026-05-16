package com.online.abaca.repository;

import com.online.abaca.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findAllByOrderHead_IdOrder(Long idOrder);
    List<OrderItem> findByProduct_Seller_IdSellerOrderByOrderHead_OrderDateDesc(Long idSeller);
}
