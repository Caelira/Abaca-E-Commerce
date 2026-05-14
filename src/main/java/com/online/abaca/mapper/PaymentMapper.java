package com.online.abaca.mapper;

import com.online.abaca.dto.PaymentRequestDTO;
import com.online.abaca.dto.PaymentResponseDTO;
import com.online.abaca.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(source = "orderHead.idOrder", target = "idOrder")
    PaymentResponseDTO toResponseDTO(Payment payment);

    @Mapping(target = "idPayment", ignore = true)
    @Mapping(target = "orderHead", ignore = true)
    @Mapping(target = "paymentDate", ignore = true)
    Payment toEntity(PaymentRequestDTO requestDTO);

    @Mapping(target = "idPayment", ignore = true)
    @Mapping(target = "orderHead", ignore = true)
    @Mapping(target = "paymentDate", ignore = true)
    void updateEntityFromDTO(PaymentRequestDTO requestDTO, @MappingTarget Payment payment);
}
