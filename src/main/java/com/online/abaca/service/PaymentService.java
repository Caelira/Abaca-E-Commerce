package com.online.abaca.service;

import com.online.abaca.dto.PaymentRequestDTO;
import com.online.abaca.dto.PaymentResponseDTO;

import java.util.List;

public interface PaymentService {
    PaymentResponseDTO createPayment(PaymentRequestDTO requestDTO);
    PaymentResponseDTO getPaymentById(Long idPayment);
    List<PaymentResponseDTO> getAllPayments();
    PaymentResponseDTO updatePayment(Long idPayment, PaymentRequestDTO requestDTO);
    void deletePayment(Long idPayment);
}