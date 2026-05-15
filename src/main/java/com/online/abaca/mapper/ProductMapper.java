package com.online.abaca.mapper;

import com.online.abaca.dto.ProductRequestDTO;
import com.online.abaca.dto.ProductResponseDTO;
import com.online.abaca.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "seller.idSeller", target = "idSeller")
    @Mapping(source = "category.idCategory", target = "idCategory")
    ProductResponseDTO toResponseDTO(Product product);

    @Mapping(target = "idProduct", ignore = true)
    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "totalSold", ignore = true)
    Product toEntity(ProductRequestDTO requestDTO);

    @Mapping(target = "idProduct", ignore = true)
    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "totalSold", ignore = true)
    void updateEntityFromDTO(ProductRequestDTO requestDTO, @MappingTarget Product product);
}