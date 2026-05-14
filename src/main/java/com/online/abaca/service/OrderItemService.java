package com.online.abaca.service;

import com.online.abaca.dto.OrderItemRequestDTO;
import com.online.abaca.dto.OrderItemResponseDTO;

import java.util.List;

public interface OrderItemService {
    OrderItemResponseDTO createOrderItem(OrderItemRequestDTO requestDTO);
    OrderItemResponseDTO getOrderItemById(Long idOrderItem);
    List<OrderItemResponseDTO> getAllOrderItems();
    OrderItemResponseDTO updateOrderItem(Long idOrderItem, OrderItemRequestDTO requestDTO);
    void deleteOrderItem(Long idOrderItem);
}
