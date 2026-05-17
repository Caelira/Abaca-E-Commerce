package com.online.abaca.uicontroller;

import com.online.abaca.dto.ProductResponseDTO;
import com.online.abaca.model.Seller;
import com.online.abaca.repository.SellerRepository;
import com.online.abaca.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SearchController {

    private final ProductService productService;
    private final SellerRepository sellerRepository;

    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model) {
        Page<ProductResponseDTO> productResults = productService.searchProducts(keyword, 0, 50);
        List<Seller> shopResults = sellerRepository.findByStoreNameContainingIgnoreCase(keyword);

        model.addAttribute("products", productResults.getContent());
        model.addAttribute("shops", shopResults);
        model.addAttribute("keyword", keyword);

        return "search-results";
    }
}