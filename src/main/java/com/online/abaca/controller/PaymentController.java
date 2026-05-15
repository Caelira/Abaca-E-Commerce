package com.online.abaca.controller;

import com.online.abaca.dto.PaymentRequestDTO;
import com.online.abaca.dto.PaymentResponseDTO;
import com.online.abaca.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> createPayment(@Valid @RequestBody PaymentRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.createPayment(requestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> getPaymentById(@PathVariable("id") Long idPayment) {
        return ResponseEntity.ok(paymentService.getPaymentById(idPayment));
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponseDTO>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> updatePayment(
            @PathVariable("id") Long idPayment,
            @Valid @RequestBody PaymentRequestDTO requestDTO) {
        return ResponseEntity.ok(paymentService.updatePayment(idPayment, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable("id") Long idPayment) {
        paymentService.deletePayment(idPayment);
        return ResponseEntity.noContent().build();
    }
}