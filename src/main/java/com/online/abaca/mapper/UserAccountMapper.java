package com.online.abaca.mapper;

import com.online.abaca.dto.UserAccountRequestDTO;
import com.online.abaca.dto.UserAccountResponseDTO;
import com.online.abaca.model.UserAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserAccountMapper {

    UserAccountResponseDTO toResponseDTO(UserAccount userAccount);

    @Mapping(target = "idUser", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    UserAccount toEntity(UserAccountRequestDTO requestDTO);

    @Mapping(target = "idUser", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromDTO(UserAccountRequestDTO requestDTO, @MappingTarget UserAccount userAccount);
}