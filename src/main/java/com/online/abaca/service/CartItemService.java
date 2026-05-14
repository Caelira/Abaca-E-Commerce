package com.online.abaca.service;

import com.online.abaca.dto.CartItemRequestDTO;
import com.online.abaca.dto.CartItemResponseDTO;

import java.util.List;

public interface CartItemService {
    CartItemResponseDTO createCartItem(CartItemRequestDTO requestDTO);
    CartItemResponseDTO getCartItemById(Long idCartItem);
    List<CartItemResponseDTO> getAllCartItems();
    CartItemResponseDTO updateCartItem(Long idCartItem, CartItemRequestDTO requestDTO);
    void deleteCartItem(Long idCartItem);
}