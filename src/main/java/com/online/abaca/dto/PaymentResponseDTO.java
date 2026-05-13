package com.online.abaca.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentResponseDTO {
    private Long idPayment;
    private Long idOrder;
    private BigDecimal paymentAmount;
    private String paymentMethod;
    private String refNumber;
    private LocalDateTime paymentDate;
}