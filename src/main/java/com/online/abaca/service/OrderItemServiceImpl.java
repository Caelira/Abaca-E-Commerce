package com.online.abaca.service;

import com.online.abaca.dto.OrderItemRequestDTO;
import com.online.abaca.dto.OrderItemResponseDTO;
import com.online.abaca.mapper.OrderItemMapper;
import com.online.abaca.model.OrderHead;
import com.online.abaca.model.OrderItem;
import com.online.abaca.model.Product;
import com.online.abaca.repository.OrderHeadRepository;
import com.online.abaca.repository.OrderItemRepository;
import com.online.abaca.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderHeadRepository orderHeadRepository;
    private final ProductRepository productRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    @Transactional
    public OrderItemResponseDTO createOrderItem(OrderItemRequestDTO requestDTO) {
        OrderHead orderHead = orderHeadRepository.findById(requestDTO.getIdOrder())
                .orElseThrow(() -> new EntityNotFoundException("OrderHead not found with id: " + requestDTO.getIdOrder()));
        Product product = productRepository.findById(requestDTO.getIdProduct())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + requestDTO.getIdProduct()));
        OrderItem orderItem = orderItemMapper.toEntity(requestDTO);
        orderItem.setOrderHead(orderHead);
        orderItem.setProduct(product);
        OrderItem savedOrderItem = orderItemRepository.save(orderItem);
        return orderItemMapper.toResponseDTO(savedOrderItem);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderItemResponseDTO getOrderItemById(Long idOrderItem) {
        OrderItem orderItem = orderItemRepository.findById(idOrderItem)
                .orElseThrow(() -> new EntityNotFoundException("OrderItem not found with id: " + idOrderItem));
        return orderItemMapper.toResponseDTO(orderItem);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderItemResponseDTO> getAllOrderItems() {
        return orderItemRepository.findAll().stream()
                .map(orderItemMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderItemResponseDTO updateOrderItem(Long idOrderItem, OrderItemRequestDTO requestDTO) {
        OrderItem existingOrderItem = orderItemRepository.findById(idOrderItem)
                .orElseThrow(() -> new EntityNotFoundException("OrderItem not found with id: " + idOrderItem));
        if (!existingOrderItem.getOrderHead().getIdOrder().equals(requestDTO.getIdOrder())) {
            OrderHead orderHead = orderHeadRepository.findById(requestDTO.getIdOrder())
                    .orElseThrow(() -> new EntityNotFoundException("OrderHead not found with id: " + requestDTO.getIdOrder()));
            existingOrderItem.setOrderHead(orderHead);
        }
        if (!existingOrderItem.getProduct().getIdProduct().equals(requestDTO.getIdProduct())) {
            Product product = productRepository.findById(requestDTO.getIdProduct())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + requestDTO.getIdProduct()));
            existingOrderItem.setProduct(product);
        }
        orderItemMapper.updateEntityFromDTO(requestDTO, existingOrderItem);
        OrderItem updatedOrderItem = orderItemRepository.save(existingOrderItem);
        return orderItemMapper.toResponseDTO(updatedOrderItem);
    }

    @Override
    @Transactional
    public void deleteOrderItem(Long idOrderItem) {
        if (!orderItemRepository.existsById(idOrderItem)) {
            throw new EntityNotFoundException("OrderItem not found with id: " + idOrderItem);
        }
        orderItemRepository.deleteById(idOrderItem);
    }
}