package com.online.abaca.repository;

import com.online.abaca.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    //List<OrderItem> findAllByOrderHead_IdOrder(Long idOrder);
    //List<OrderItem> findByProduct_Seller_IdSellerOrderByOrderHead_OrderDateDesc(Long idSeller);
    @Query("SELECT oi FROM OrderItem oi " +
            "JOIN FETCH oi.product p " +
            "JOIN FETCH oi.orderHead oh " +
            "WHERE oh.idOrder = :idOrder")
    List<OrderItem> findAllByOrderHead_IdOrder(@Param("idOrder") Long idOrder);

    @Query("SELECT oi FROM OrderItem oi " +
            "JOIN FETCH oi.product p " +
            "JOIN FETCH oi.orderHead oh " +
            "WHERE p.seller.idSeller = :idSeller " +
            "ORDER BY oh.orderDate DESC")
    List<OrderItem> findByProduct_Seller_IdSellerOrderByOrderHead_OrderDateDesc(@Param("idSeller") Long idSeller);
}
