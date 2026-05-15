package com.online.abaca.controller;

import com.online.abaca.dto.CartItemRequestDTO;
import com.online.abaca.dto.CartItemResponseDTO;
import com.online.abaca.service.CartItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart-items")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

    @PostMapping
    public ResponseEntity<CartItemResponseDTO> createCartItem(@Valid @RequestBody CartItemRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemService.createCartItem(requestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartItemResponseDTO> getCartItemById(@PathVariable("id") Long idCartItem) {
        return ResponseEntity.ok(cartItemService.getCartItemById(idCartItem));
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponseDTO>> getAllCartItems() {
        return ResponseEntity.ok(cartItemService.getAllCartItems());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartItemResponseDTO> updateCartItem(
            @PathVariable("id") Long idCartItem,
            @Valid @RequestBody CartItemRequestDTO requestDTO) {
        return ResponseEntity.ok(cartItemService.updateCartItem(idCartItem, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable("id") Long idCartItem) {
        cartItemService.deleteCartItem(idCartItem);
        return ResponseEntity.noContent().build();
    }
}