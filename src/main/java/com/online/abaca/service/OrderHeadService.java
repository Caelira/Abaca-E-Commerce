package com.online.abaca.service;

import com.online.abaca.dto.OrderHeadRequestDTO;
import com.online.abaca.dto.OrderHeadResponseDTO;

import java.util.List;

public interface OrderHeadService {
    OrderHeadResponseDTO createOrderHead(OrderHeadRequestDTO requestDTO);
    OrderHeadResponseDTO getOrderHeadById(Long idOrder);
    List<OrderHeadResponseDTO> getAllOrderHeads();
    OrderHeadResponseDTO updateOrderHead(Long idOrder, OrderHeadRequestDTO requestDTO);
    void deleteOrderHead(Long idOrder);
}