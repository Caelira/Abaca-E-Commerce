package com.online.abaca.mapper;

import com.online.abaca.dto.OrderItemRequestDTO;
import com.online.abaca.dto.OrderItemResponseDTO;
import com.online.abaca.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(source = "orderHead.idOrder", target = "idOrder")
    @Mapping(source = "product.idProduct", target = "idProduct")
    OrderItemResponseDTO toResponseDTO(OrderItem orderItem);

    @Mapping(target = "idOrderItem", ignore = true)
    @Mapping(target = "orderHead", ignore = true)
    @Mapping(target = "product", ignore = true)
    OrderItem toEntity(OrderItemRequestDTO requestDTO);

    @Mapping(target = "idOrderItem", ignore = true)
    @Mapping(target = "orderHead", ignore = true)
    @Mapping(target = "product", ignore = true)
    void updateEntityFromDTO(OrderItemRequestDTO requestDTO, @MappingTarget OrderItem orderItem);
}