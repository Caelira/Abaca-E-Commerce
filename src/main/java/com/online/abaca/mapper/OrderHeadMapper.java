package com.online.abaca.mapper;

import com.online.abaca.dto.OrderHeadRequestDTO;
import com.online.abaca.dto.OrderHeadResponseDTO;
import com.online.abaca.model.OrderHead;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderHeadMapper {

    @Mapping(source = "buyer.idBuyer", target = "idBuyer")
    @Mapping(source = "cartHead.idCart", target = "idCart")
    @Mapping(source = "shippingAddress.idAddress", target = "idShippingAddress")
    OrderHeadResponseDTO toResponseDTO(OrderHead orderHead);

    @Mapping(target = "idOrder", ignore = true)
    @Mapping(target = "buyer", ignore = true)
    @Mapping(target = "cartHead", ignore = true)
    @Mapping(target = "shippingAddress", ignore = true)
    @Mapping(target = "orderDate", ignore = true)
    OrderHead toEntity(OrderHeadRequestDTO requestDTO);

    @Mapping(target = "idOrder", ignore = true)
    @Mapping(target = "buyer", ignore = true)
    @Mapping(target = "cartHead", ignore = true)
    @Mapping(target = "shippingAddress", ignore = true)
    @Mapping(target = "orderDate", ignore = true)
    void updateEntityFromDTO(OrderHeadRequestDTO requestDTO, @MappingTarget OrderHead orderHead);
}
