package com.online.abaca.mapper;
import com.online.abaca.dto.CategoryRequestDTO;
import com.online.abaca.dto.CategoryResponseDTO;
import com.online.abaca.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponseDTO toResponseDTO(Category category);

    @Mapping(target = "idCategory", ignore = true)
    Category toEntity(CategoryRequestDTO requestDTO);

    @Mapping(target = "idCategory", ignore = true)
    void updateEntityFromDTO(CategoryRequestDTO requestDTO, @MappingTarget Category category);
}