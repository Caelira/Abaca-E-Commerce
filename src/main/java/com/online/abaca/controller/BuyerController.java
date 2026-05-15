package com.online.abaca.controller;

import com.online.abaca.dto.BuyerRequestDTO;
import com.online.abaca.dto.BuyerResponseDTO;
import com.online.abaca.service.BuyerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/buyers")
@RequiredArgsConstructor
public class BuyerController {

    private final BuyerService buyerService;

    @PostMapping
    public ResponseEntity<BuyerResponseDTO> createBuyer(@Valid @RequestBody BuyerRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(buyerService.createBuyer(requestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuyerResponseDTO> getBuyerById(@PathVariable("id") Long idBuyer) {
        return ResponseEntity.ok(buyerService.getBuyerById(idBuyer));
    }

    @GetMapping
    public ResponseEntity<List<BuyerResponseDTO>> getAllBuyers() {
        return ResponseEntity.ok(buyerService.getAllBuyers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BuyerResponseDTO> updateBuyer(
            @PathVariable("id") Long idBuyer,
            @Valid @RequestBody BuyerRequestDTO requestDTO) {
        return ResponseEntity.ok(buyerService.updateBuyer(idBuyer, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBuyer(@PathVariable("id") Long idBuyer) {
        buyerService.deleteBuyer(idBuyer);
        return ResponseEntity.noContent().build();
    }
}