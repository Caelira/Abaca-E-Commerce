package com.online.abaca.mapper;

import com.online.abaca.dto.BuyerRequestDTO;
import com.online.abaca.dto.BuyerResponseDTO;
import com.online.abaca.model.Buyer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BuyerMapper {

    @Mapping(source = "userAccount.idUser", target = "idUser")
    BuyerResponseDTO toResponseDTO(Buyer buyer);

    @Mapping(target = "idBuyer", ignore = true)
    @Mapping(target = "userAccount", ignore = true)
    Buyer toEntity(BuyerRequestDTO requestDTO);

    @Mapping(target = "idBuyer", ignore = true)
    @Mapping(target = "userAccount", ignore = true)
    void updateEntityFromDTO(BuyerRequestDTO requestDTO, @MappingTarget Buyer buyer);
}