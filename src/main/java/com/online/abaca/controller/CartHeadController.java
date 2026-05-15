package com.online.abaca.controller;

import com.online.abaca.dto.CartHeadRequestDTO;
import com.online.abaca.dto.CartHeadResponseDTO;
import com.online.abaca.service.CartHeadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart-heads")
@RequiredArgsConstructor
public class CartHeadController {

    private final CartHeadService cartHeadService;

    @PostMapping
    public ResponseEntity<CartHeadResponseDTO> createCartHead(@Valid @RequestBody CartHeadRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartHeadService.createCartHead(requestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartHeadResponseDTO> getCartHeadById(@PathVariable("id") Long idCart) {
        return ResponseEntity.ok(cartHeadService.getCartHeadById(idCart));
    }

    @GetMapping
    public ResponseEntity<List<CartHeadResponseDTO>> getAllCartHeads() {
        return ResponseEntity.ok(cartHeadService.getAllCartHeads());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartHeadResponseDTO> updateCartHead(
            @PathVariable("id") Long idCart,
            @Valid @RequestBody CartHeadRequestDTO requestDTO) {
        return ResponseEntity.ok(cartHeadService.updateCartHead(idCart, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartHead(@PathVariable("id") Long idCart) {
        cartHeadService.deleteCartHead(idCart);
        return ResponseEntity.noContent().build();
    }
}