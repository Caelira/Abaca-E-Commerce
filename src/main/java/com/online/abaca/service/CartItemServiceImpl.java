package com.online.abaca.service;

import com.online.abaca.dto.CartItemRequestDTO;
import com.online.abaca.dto.CartItemResponseDTO;
import com.online.abaca.mapper.CartItemMapper;
import com.online.abaca.model.Buyer;
import com.online.abaca.model.CartHead;
import com.online.abaca.model.CartItem;
import com.online.abaca.model.Product;
import com.online.abaca.repository.BuyerRepository;
import com.online.abaca.repository.CartHeadRepository;
import com.online.abaca.repository.CartItemRepository;
import com.online.abaca.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartHeadRepository cartHeadRepository;
    private final ProductRepository productRepository;
    private final CartItemMapper cartItemMapper;
    private final BuyerRepository buyerRepository;


    @Override
    @Transactional
    public void removeCartItem(Long idUser, Long idCartItem) {
        Buyer buyer = buyerRepository.findByUserAccount_IdUser(idUser)
                .orElseThrow(() -> new EntityNotFoundException("Buyer not found"));
        CartHead cart = cartHeadRepository.findByBuyer_IdBuyerAndStatus(buyer.getIdBuyer(), "ACTIVE")
                .orElseThrow(() -> new EntityNotFoundException("Active cart not found"));

        CartItem item = cartItemRepository.findById(idCartItem)
                .orElseThrow(() -> new EntityNotFoundException("Cart item not found"));

        if (item.getCartHead().getIdCart().equals(cart.getIdCart())) {
            cartItemRepository.delete(item);
        }
    }

    @Override
    @Transactional
    public void addItemToCart(Long idUser, Long idProduct, Integer quantity) {
        Buyer buyer = buyerRepository.findByUserAccount_IdUser(idUser)
                .orElseThrow(() -> new EntityNotFoundException("Buyer profile not found"));

        CartHead cart = cartHeadRepository.findByBuyer_IdBuyerAndStatus(buyer.getIdBuyer(), "ACTIVE")
                .orElseGet(() -> {
                    CartHead newCart = new CartHead();
                    newCart.setBuyer(buyer);
                    newCart.setStatus("ACTIVE");
                    return cartHeadRepository.save(newCart);
                });

        Product product = productRepository.findById(idProduct)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        if (product.getStockQuantity() < quantity) {
            throw new IllegalStateException("Not enough stock available.");
        }

        Optional<CartItem> existingItemOpt = cartItemRepository.findByCartHead_IdCartAndProduct_IdProduct(cart.getIdCart(), idProduct);

        if (existingItemOpt.isPresent()) {
            CartItem existingItem = existingItemOpt.get();
            int newQuantity = existingItem.getQuantity() + quantity;

            if (product.getStockQuantity() < newQuantity) {
                throw new IllegalStateException("Adding this would exceed available stock.");
            }

            existingItem.setQuantity(newQuantity);
            cartItemRepository.save(existingItem);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCartHead(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cartItemRepository.save(newItem);
        }
    }

    @Override
    @Transactional
    public CartItemResponseDTO createCartItem(CartItemRequestDTO requestDTO) {
        CartHead cartHead = cartHeadRepository.findById(requestDTO.getIdCart())
                .orElseThrow(() -> new EntityNotFoundException("CartHead not found with id: " + requestDTO.getIdCart()));
        Product product = productRepository.findById(requestDTO.getIdProduct())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + requestDTO.getIdProduct()));
        CartItem cartItem = cartItemMapper.toEntity(requestDTO);
        cartItem.setCartHead(cartHead);
        cartItem.setProduct(product);
        CartItem savedCartItem = cartItemRepository.save(cartItem);
        return cartItemMapper.toResponseDTO(savedCartItem);
    }

    @Override
    @Transactional(readOnly = true)
    public CartItemResponseDTO getCartItemById(Long idCartItem) {
        CartItem cartItem = cartItemRepository.findById(idCartItem)
                .orElseThrow(() -> new EntityNotFoundException("CartItem not found with id: " + idCartItem));
        return cartItemMapper.toResponseDTO(cartItem);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartItemResponseDTO> getAllCartItems() {
        return cartItemRepository.findAll().stream()
                .map(cartItemMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CartItemResponseDTO updateCartItem(Long idCartItem, CartItemRequestDTO requestDTO) {
        CartItem existingCartItem = cartItemRepository.findById(idCartItem)
                .orElseThrow(() -> new EntityNotFoundException("CartItem not found with id: " + idCartItem));
        if (!existingCartItem.getCartHead().getIdCart().equals(requestDTO.getIdCart())) {
            CartHead cartHead = cartHeadRepository.findById(requestDTO.getIdCart())
                    .orElseThrow(() -> new EntityNotFoundException("CartHead not found with id: " + requestDTO.getIdCart()));
            existingCartItem.setCartHead(cartHead);
        }
        if (!existingCartItem.getProduct().getIdProduct().equals(requestDTO.getIdProduct())) {
            Product product = productRepository.findById(requestDTO.getIdProduct())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + requestDTO.getIdProduct()));
            existingCartItem.setProduct(product);
        }
        cartItemMapper.updateEntityFromDTO(requestDTO, existingCartItem);
        CartItem updatedCartItem = cartItemRepository.save(existingCartItem);
        return cartItemMapper.toResponseDTO(updatedCartItem);
    }

    @Override
    @Transactional
    public void deleteCartItem(Long idCartItem) {
        if (!cartItemRepository.existsById(idCartItem)) {
            throw new EntityNotFoundException("CartItem not found with id: " + idCartItem);
        }
        cartItemRepository.deleteById(idCartItem);
    }
}