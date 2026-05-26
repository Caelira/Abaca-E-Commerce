package com.online.abaca.repository;

import com.online.abaca.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByDeletedFalseOrderByCreatedAtDesc(Pageable pageable);

    Page<Product> findAllByCategory_IdCategoryAndDeletedFalseOrderByCreatedAtDesc(Long idCategory, Pageable pageable);

    List<Product> findBySeller_IdSellerAndDeletedFalseOrderByCreatedAtDesc(Long idSeller);

    Page<Product> findByProductNameContainingIgnoreCaseAndDeletedFalseOrderByCreatedAtDesc(String keyword, Pageable pageable);
}