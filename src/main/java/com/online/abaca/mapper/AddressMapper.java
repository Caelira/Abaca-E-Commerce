package com.online.abaca.mapper;

import com.online.abaca.dto.AddressRequestDTO;
import com.online.abaca.dto.AddressResponseDTO;
import com.online.abaca.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(source = "userAccount.idUser", target = "idUser")
    AddressResponseDTO toResponseDTO(Address address);

    @Mapping(target = "userAccount", ignore = true)
    @Mapping(target = "idAddress", ignore = true)
    Address toEntity(AddressRequestDTO requestDTO);

    @Mapping(target = "userAccount", ignore = true)
    @Mapping(target = "idAddress", ignore = true)
    void updateEntityFromDTO(AddressRequestDTO requestDTO, @MappingTarget Address address);
}