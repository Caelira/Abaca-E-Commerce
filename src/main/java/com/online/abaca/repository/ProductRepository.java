package com.online.abaca.repository;

import com.online.abaca.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Page<Product> findAllByCategory_IdCategoryOrderByCreatedAtDesc(Long idCategory, Pageable pageable);
}
