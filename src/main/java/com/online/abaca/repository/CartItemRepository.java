package com.online.abaca.repository;

import com.online.abaca.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("SELECT SUM(c.quantity) FROM CartItem c WHERE c.cartHead.buyer.userAccount.idUser = :idUser AND c.cartHead.status = 'ACTIVE'")
    Integer sumQuantityByUserId(@Param("idUser") Long idUser);

    List<CartItem> findAllByCartHead_IdCart(Long idCart);

    Optional<CartItem> findByCartHead_IdCartAndProduct_IdProduct(Long idCart, Long idProduct);

    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.product.idProduct = :idProduct")
    void deleteByProduct_IdProduct(@Param("idProduct") Long idProduct);
}