package com.online.abaca.controller;

import com.online.abaca.dto.OrderItemRequestDTO;
import com.online.abaca.dto.OrderItemResponseDTO;
import com.online.abaca.service.OrderItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-items")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @PostMapping
    public ResponseEntity<OrderItemResponseDTO> createOrderItem(@Valid @RequestBody OrderItemRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderItemService.createOrderItem(requestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemResponseDTO> getOrderItemById(@PathVariable("id") Long idOrderItem) {
        return ResponseEntity.ok(orderItemService.getOrderItemById(idOrderItem));
    }

    @GetMapping
    public ResponseEntity<List<OrderItemResponseDTO>> getAllOrderItems() {
        return ResponseEntity.ok(orderItemService.getAllOrderItems());
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItemResponseDTO> updateOrderItem(
            @PathVariable("id") Long idOrderItem,
            @Valid @RequestBody OrderItemRequestDTO requestDTO) {
        return ResponseEntity.ok(orderItemService.updateOrderItem(idOrderItem, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable("id") Long idOrderItem) {
        orderItemService.deleteOrderItem(idOrderItem);
        return ResponseEntity.noContent().build();
    }
}