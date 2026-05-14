package com.online.abaca.service;

import com.online.abaca.dto.PaymentRequestDTO;
import com.online.abaca.dto.PaymentResponseDTO;
import com.online.abaca.mapper.PaymentMapper;
import com.online.abaca.model.OrderHead;
import com.online.abaca.model.Payment;
import com.online.abaca.repository.OrderHeadRepository;
import com.online.abaca.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderHeadRepository orderHeadRepository;
    private final PaymentMapper paymentMapper;

    @Override
    @Transactional
    public PaymentResponseDTO createPayment(PaymentRequestDTO requestDTO) {
        OrderHead orderHead = orderHeadRepository.findById(requestDTO.getIdOrder())
                .orElseThrow(() -> new EntityNotFoundException("OrderHead not found with id: " + requestDTO.getIdOrder()));
        Payment payment = paymentMapper.toEntity(requestDTO);
        payment.setOrderHead(orderHead);
        payment.setPaymentDate(LocalDateTime.now());
        Payment savedPayment = paymentRepository.save(payment);
        return paymentMapper.toResponseDTO(savedPayment);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponseDTO getPaymentById(Long idPayment) {
        Payment payment = paymentRepository.findById(idPayment)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with id: " + idPayment));
        return paymentMapper.toResponseDTO(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponseDTO> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(paymentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PaymentResponseDTO updatePayment(Long idPayment, PaymentRequestDTO requestDTO) {
        Payment existingPayment = paymentRepository.findById(idPayment)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with id: " + idPayment));
        if (!existingPayment.getOrderHead().getIdOrder().equals(requestDTO.getIdOrder())) {
            OrderHead orderHead = orderHeadRepository.findById(requestDTO.getIdOrder())
                    .orElseThrow(() -> new EntityNotFoundException("OrderHead not found with id: " + requestDTO.getIdOrder()));
            existingPayment.setOrderHead(orderHead);
        }
        paymentMapper.updateEntityFromDTO(requestDTO, existingPayment);
        Payment updatedPayment = paymentRepository.save(existingPayment);
        return paymentMapper.toResponseDTO(updatedPayment);
    }

    @Override
    @Transactional
    public void deletePayment(Long idPayment) {
        if (!paymentRepository.existsById(idPayment)) {
            throw new EntityNotFoundException("Payment not found with id: " + idPayment);
        }
        paymentRepository.deleteById(idPayment);
    }
}