package com.online.abaca.mapper;

import com.online.abaca.dto.CartItemRequestDTO;
import com.online.abaca.dto.CartItemResponseDTO;
import com.online.abaca.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    @Mapping(source = "cartHead.idCart", target = "idCart")
    @Mapping(source = "product.idProduct", target = "idProduct")
    CartItemResponseDTO toResponseDTO(CartItem cartItem);

    @Mapping(target = "idCartItem", ignore = true)
    @Mapping(target = "cartHead", ignore = true)
    @Mapping(target = "product", ignore = true)
    CartItem toEntity(CartItemRequestDTO requestDTO);

    @Mapping(target = "idCartItem", ignore = true)
    @Mapping(target = "cartHead", ignore = true)
    @Mapping(target = "product", ignore = true)
    void updateEntityFromDTO(CartItemRequestDTO requestDTO, @MappingTarget CartItem cartItem);
}
