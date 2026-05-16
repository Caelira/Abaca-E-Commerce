package com.online.abaca.uicontroller;

import com.online.abaca.dto.ProductResponseDTO;
import com.online.abaca.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class SearchController {

    private final ProductService productService;

    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model) {
        Page<ProductResponseDTO> results = productService.searchProducts(keyword, 0, 50); // Get top 50 results
        model.addAttribute("products", results.getContent());
        model.addAttribute("keyword", keyword);
        return "search-results";
    }
}