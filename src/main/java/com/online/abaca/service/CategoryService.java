package com.online.abaca.service;

import com.online.abaca.dto.CategoryRequestDTO;
import com.online.abaca.dto.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {
    CategoryResponseDTO createCategory(CategoryRequestDTO requestDTO);
    CategoryResponseDTO getCategoryById(Long idCategory);
    List<CategoryResponseDTO> getAllCategories();
    CategoryResponseDTO updateCategory(Long idCategory, CategoryRequestDTO requestDTO);
    void deleteCategory(Long idCategory);
}