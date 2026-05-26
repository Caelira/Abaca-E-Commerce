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
import com.online.abaca.repository.CartItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final CategoryRepository categoryRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> getProductsByCategory(Long idCategory, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAllByCategory_IdCategoryAndDeletedFalseOrderByCreatedAtDesc(idCategory, pageable)
                .map(productMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getProductsBySellerId(Long idSeller) {
        return productRepository.findBySeller_IdSellerAndDeletedFalseOrderByCreatedAtDesc(idSeller).stream()
                .map(productMapper::toResponseDTO)
                .toList();
    }

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
        product.setDeleted(false);

        Product savedProduct = productRepository.save(product);
        return productMapper.toResponseDTO(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDTO getProductById(Long idProduct) {
        Product product = productRepository.findById(idProduct)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + idProduct));
        if (product.isDeleted()) {
            throw new EntityNotFoundException("Product has been removed.");
        }
        return productMapper.toResponseDTO(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .filter(p -> !p.isDeleted())
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
                    .orElseThrow(() -> new EntityNotFoundException("Seller not found"));
            existingProduct.setSeller(seller);
        }
        if (!existingProduct.getCategory().getIdCategory().equals(requestDTO.getIdCategory())) {
            Category category = categoryRepository.findById(requestDTO.getIdCategory())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found"));
            existingProduct.setCategory(category);
        }
        productMapper.updateEntityFromDTO(requestDTO, existingProduct);
        Product updatedProduct = productRepository.save(existingProduct);
        return productMapper.toResponseDTO(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long idProduct) {
        Product existingProduct = productRepository.findById(idProduct)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        cartItemRepository.deleteByProduct_IdProduct(idProduct);
        existingProduct.setDeleted(true);
        productRepository.save(existingProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> getDailyDiscoverFeed(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAllByDeletedFalseOrderByCreatedAtDesc(pageable)
                .map(productMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public byte[] getProductImage(Long idProduct) {
        Product product = productRepository.findById(idProduct)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        return product.getProductImage();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> searchProducts(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findByProductNameContainingIgnoreCaseAndDeletedFalseOrderByCreatedAtDesc(keyword, pageable)
                .map(productMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public void updateProduct(Long idProduct, Long idSeller, ProductRequestDTO requestDTO, MultipartFile imageFile) throws Exception {
        Product existingProduct = productRepository.findById(idProduct)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        if (!existingProduct.getSeller().getIdSeller().equals(idSeller)) {
            throw new IllegalStateException("You do not have permission to edit this product.");
        }

        existingProduct.setProductName(requestDTO.getProductName());
        existingProduct.setProductPrice(requestDTO.getProductPrice());
        existingProduct.setOriginalPrice(requestDTO.getOriginalPrice());
        existingProduct.setStockQuantity(requestDTO.getStockQuantity());

        if (requestDTO.getIdCategory() != null) {
            Category category = categoryRepository.findById(requestDTO.getIdCategory()).orElseThrow();
            existingProduct.setCategory(category);
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            existingProduct.setProductImage(imageFile.getBytes());
        }

        productRepository.save(existingProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long idProduct, Long idSeller) {
        Product existingProduct = productRepository.findById(idProduct)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        if (!existingProduct.getSeller().getIdSeller().equals(idSeller)) {
            throw new IllegalStateException("You do not have permission to delete this product.");
        }

        cartItemRepository.deleteByProduct_IdProduct(idProduct);

        existingProduct.setDeleted(true);
        productRepository.save(existingProduct);
    }
}