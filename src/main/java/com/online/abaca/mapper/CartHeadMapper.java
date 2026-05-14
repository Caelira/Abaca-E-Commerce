package com.online.abaca.mapper;

import com.online.abaca.dto.CartHeadRequestDTO;
import com.online.abaca.dto.CartHeadResponseDTO;
import com.online.abaca.model.CartHead;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CartHeadMapper {

    @Mapping(source = "buyer.idBuyer", target = "idBuyer")
    CartHeadResponseDTO toResponseDTO(CartHead cartHead);

    @Mapping(target = "idCart", ignore = true)
    @Mapping(target = "buyer", ignore = true)
    CartHead toEntity(CartHeadRequestDTO requestDTO);

    @Mapping(target = "idCart", ignore = true)
    @Mapping(target = "buyer", ignore = true)
    void updateEntityFromDTO(CartHeadRequestDTO requestDTO, @MappingTarget CartHead cartHead);
}