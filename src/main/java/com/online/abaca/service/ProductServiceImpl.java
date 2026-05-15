package com.online.abaca.service;
import com.online.abaca.dto.ProductRequestDTO;
import com.online.abaca.dto.ProductResponseDTO;
import com.online.abaca.mapper.ProductMapper;
import com.online.abaca.model.Category;
import com.online.abaca.model.Product;
import com.online.abaca.model.Seller;
import com.online.abaca.repository.CategoryRepository;
import com.online.abaca.repository.ProductRepository;
import com.online.abaca.repository.SellerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO) {
        Seller seller = sellerRepository.findById(requestDTO.getIdSeller())
                .orElseThrow(() -> new EntityNotFoundException("Seller not found"));
        Category category = categoryRepository.findById(requestDTO.getIdCategory())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        Product product = productMapper.toEntity(requestDTO);
        product.setSeller(seller);
        product.setCategory(category);
        product.setCreatedAt(LocalDateTime.now());
        product.setTotalSold(0);

        Product savedProduct = productRepository.save(product);
        return productMapper.toResponseDTO(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDTO getProductById(Long idProduct) {
        Product product = productRepository.findById(idProduct)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + idProduct));
        return productMapper.toResponseDTO(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductResponseDTO updateProduct(Long idProduct, ProductRequestDTO requestDTO) {
        Product existingProduct = productRepository.findById(idProduct)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + idProduct));
        if (!existingProduct.getSeller().getIdSeller().equals(requestDTO.getIdSeller())) {
            Seller seller = sellerRepository.findById(requestDTO.getIdSeller())
                    .orElseThrow(() -> new EntityNotFoundException("Seller not found with id: " + requestDTO.getIdSeller()));
            existingProduct.setSeller(seller);
        }
        if (!existingProduct.getCategory().getIdCategory().equals(requestDTO.getIdCategory())) {
            Category category = categoryRepository.findById(requestDTO.getIdCategory())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + requestDTO.getIdCategory()));
            existingProduct.setCategory(category);
        }
        productMapper.updateEntityFromDTO(requestDTO, existingProduct);
        Product updatedProduct = productRepository.save(existingProduct);
        return productMapper.toResponseDTO(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long idProduct) {
        if (!productRepository.existsById(idProduct)) {
            throw new EntityNotFoundException("Product not found with id: " + idProduct);
        }
        productRepository.deleteById(idProduct);
    }
}