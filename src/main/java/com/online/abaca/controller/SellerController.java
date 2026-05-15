package com.online.abaca.controller;

import com.online.abaca.dto.SellerRequestDTO;
import com.online.abaca.dto.SellerResponseDTO;
import com.online.abaca.service.SellerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sellers")
@RequiredArgsConstructor
public class SellerController {

    private final SellerService sellerService;

    @PostMapping
    public ResponseEntity<SellerResponseDTO> createSeller(@Valid @RequestBody SellerRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sellerService.createSeller(requestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SellerResponseDTO> getSellerById(@PathVariable("id") Long idSeller) {
        return ResponseEntity.ok(sellerService.getSellerById(idSeller));
    }

    @GetMapping
    public ResponseEntity<List<SellerResponseDTO>> getAllSellers() {
        return ResponseEntity.ok(sellerService.getAllSellers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SellerResponseDTO> updateSeller(
            @PathVariable("id") Long idSeller,
            @Valid @RequestBody SellerRequestDTO requestDTO) {
        return ResponseEntity.ok(sellerService.updateSeller(idSeller, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable("id") Long idSeller) {
        sellerService.deleteSeller(idSeller);
        return ResponseEntity.noContent().build();
    }
}