package com.online.abaca.service;

import com.online.abaca.dto.CategoryRequestDTO;
import com.online.abaca.dto.CategoryResponseDTO;
import com.online.abaca.mapper.CategoryMapper;
import com.online.abaca.model.Category;
import com.online.abaca.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public CategoryResponseDTO createCategory(CategoryRequestDTO requestDTO) {
        Category category = categoryMapper.toEntity(requestDTO);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toResponseDTO(savedCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponseDTO getCategoryById(Long idCategory) {
        Category category = categoryRepository.findById(idCategory)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + idCategory));
        return categoryMapper.toResponseDTO(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CategoryResponseDTO updateCategory(Long idCategory, CategoryRequestDTO requestDTO) {
        Category existingCategory = categoryRepository.findById(idCategory)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + idCategory));
        categoryMapper.updateEntityFromDTO(requestDTO, existingCategory);
        Category updatedCategory = categoryRepository.save(existingCategory);
        return categoryMapper.toResponseDTO(updatedCategory);
    }

    @Override
    @Transactional
    public void deleteCategory(Long idCategory) {
        if (!categoryRepository.existsById(idCategory)) {
            throw new EntityNotFoundException("Category not found with id: " + idCategory);
        }
        categoryRepository.deleteById(idCategory);
    }
}