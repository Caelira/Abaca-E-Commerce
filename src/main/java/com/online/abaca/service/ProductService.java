package com.online.abaca.service;

import com.online.abaca.dto.ProductRequestDTO;
import com.online.abaca.dto.ProductResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    ProductResponseDTO createProduct(ProductRequestDTO requestDTO);
    ProductResponseDTO getProductById(Long idProduct);
    List<ProductResponseDTO> getAllProducts();
    ProductResponseDTO updateProduct(Long idProduct, ProductRequestDTO requestDTO);
    void deleteProduct(Long idProduct);

    @Transactional(readOnly = true)
    Page<ProductResponseDTO> getDailyDiscoverFeed(int page, int size);

    @Transactional(readOnly = true)
    byte[] getProductImage(Long idProduct);
    Page<ProductResponseDTO> getProductsByCategory(Long idCategory, int page, int size);
    List<ProductResponseDTO> getProductsBySellerId(Long idSeller);
    Page<ProductResponseDTO> searchProducts(String keyword, int page, int size);
    void updateProduct(Long idProduct, Long idSeller, ProductRequestDTO requestDTO, MultipartFile imageFile) throws Exception;
    void deleteProduct(Long idProduct, Long idSeller);
}