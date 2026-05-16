package com.online.abaca.uicontroller;

import com.online.abaca.dto.ProductResponseDTO;
import com.online.abaca.service.CategoryService;
import com.online.abaca.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CategoryService categoryService;
    private final ProductService productService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());

         Page<ProductResponseDTO> productFeed = productService.getDailyDiscoverFeed(0, 12);
        model.addAttribute("products", productFeed.getContent());

        return "index";
    }

    @GetMapping("/products/feed")
    public String loadMoreProducts(@RequestParam("page") int page, Model model) {
        Page<ProductResponseDTO> productFeed = productService.getDailyDiscoverFeed(page, 12);
        model.addAttribute("products", productFeed.getContent());

         return "fragments/product-cards :: productGrid";
    }
}