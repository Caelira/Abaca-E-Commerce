package com.online.abaca.service;

import com.online.abaca.dto.CartHeadRequestDTO;
import com.online.abaca.dto.CartHeadResponseDTO;

import java.util.List;

public interface CartHeadService {
    CartHeadResponseDTO createCartHead(CartHeadRequestDTO requestDTO);
    CartHeadResponseDTO getCartHeadById(Long idCart);
    List<CartHeadResponseDTO> getAllCartHeads();
    CartHeadResponseDTO updateCartHead(Long idCart, CartHeadRequestDTO requestDTO);
    void deleteCartHead(Long idCart);
}