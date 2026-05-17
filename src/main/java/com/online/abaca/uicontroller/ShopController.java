package com.online.abaca.uicontroller;

import com.online.abaca.model.Seller;
import com.online.abaca.repository.SellerRepository;
import com.online.abaca.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ShopController {

    private final SellerRepository sellerRepository;
    private final ProductService productService;

    @GetMapping("/shop/{id}")
    public String viewShop(@PathVariable("id") Long idSeller, Model model) {
        Seller seller = sellerRepository.findById(idSeller)
                .orElseThrow(() -> new RuntimeException("Shop not found"));

        model.addAttribute("seller", seller);
        model.addAttribute("products", productService.getProductsBySellerId(idSeller));

        return "shop-view";
    }
}
