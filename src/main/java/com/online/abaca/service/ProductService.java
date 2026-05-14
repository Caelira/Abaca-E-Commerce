package com.online.abaca.service;

import com.online.abaca.dto.ProductRequestDTO;
import com.online.abaca.dto.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    ProductResponseDTO createProduct(ProductRequestDTO requestDTO);
    ProductResponseDTO getProductById(Long idProduct);
    List<ProductResponseDTO> getAllProducts();
    ProductResponseDTO updateProduct(Long idProduct, ProductRequestDTO requestDTO);
    void deleteProduct(Long idProduct);
}