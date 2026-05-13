package com.online.abaca.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class PaymentRequestDTO {

    @NotNull(message = "Order ID is required")
    private Long idOrder;

    @NotNull(message = "Payment amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal paymentAmount;

    @NotBlank(message = "Payment method is required")
    private String paymentMethod;

    private String refNumber;
}