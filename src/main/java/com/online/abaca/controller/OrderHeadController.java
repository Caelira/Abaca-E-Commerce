package com.online.abaca.controller;

import com.online.abaca.dto.OrderHeadRequestDTO;
import com.online.abaca.dto.OrderHeadResponseDTO;
import com.online.abaca.service.OrderHeadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderHeadController {

    private final OrderHeadService orderHeadService;

    @PostMapping
    public ResponseEntity<OrderHeadResponseDTO> createOrderHead(@Valid @RequestBody OrderHeadRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderHeadService.createOrderHead(requestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderHeadResponseDTO> getOrderHeadById(@PathVariable("id") Long idOrder) {
        return ResponseEntity.ok(orderHeadService.getOrderHeadById(idOrder));
    }

    @GetMapping
    public ResponseEntity<List<OrderHeadResponseDTO>> getAllOrderHeads() {
        return ResponseEntity.ok(orderHeadService.getAllOrderHeads());
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderHeadResponseDTO> updateOrderHead(
            @PathVariable("id") Long idOrder,
            @Valid @RequestBody OrderHeadRequestDTO requestDTO) {
        return ResponseEntity.ok(orderHeadService.updateOrderHead(idOrder, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderHead(@PathVariable("id") Long idOrder) {
        orderHeadService.deleteOrderHead(idOrder);
        return ResponseEntity.noContent().build();
    }
}