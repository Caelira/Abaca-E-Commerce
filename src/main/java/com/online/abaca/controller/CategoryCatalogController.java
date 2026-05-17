package com.online.abaca.controller;

import com.online.abaca.dto.ProductResponseDTO;
import com.online.abaca.service.CategoryService;
import com.online.abaca.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CategoryCatalogController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/category/{id}")
    public String viewCategory(@PathVariable("id") Long idCategory, Model model) {
        model.addAttribute("category", categoryService.getCategoryById(idCategory));

        Page<ProductResponseDTO> productFeed = productService.getProductsByCategory(idCategory, 0, 12);
        model.addAttribute("products", productFeed.getContent());

        return "category-view";
    }

    @GetMapping("/category/{id}/feed")
    public String getCategoryFeed(
            @PathVariable("id") Long idCategory,
            @RequestParam(value = "page", defaultValue = "1") int page,
            Model model) {

        Page<ProductResponseDTO> productFeed = productService.getProductsByCategory(idCategory, page, 12);

        model.addAttribute("products", productFeed.getContent());

         return "fragments/product-cards :: productGrid";
    }
}