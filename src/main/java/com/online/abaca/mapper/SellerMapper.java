package com.online.abaca.mapper;

import com.online.abaca.dto.SellerRequestDTO;
import com.online.abaca.dto.SellerResponseDTO;
import com.online.abaca.model.Seller;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SellerMapper {

    @Mapping(source = "userAccount.idUser", target = "idUser")
    SellerResponseDTO toResponseDTO(Seller seller);

    @Mapping(target = "idSeller", ignore = true)
    @Mapping(target = "userAccount", ignore = true)
    Seller toEntity(SellerRequestDTO requestDTO);

    @Mapping(target = "idSeller", ignore = true)
    @Mapping(target = "userAccount", ignore = true)
    void updateEntityFromDTO(SellerRequestDTO requestDTO, @MappingTarget Seller seller);
}