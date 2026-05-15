package com.online.abaca.repository;

import com.online.abaca.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
