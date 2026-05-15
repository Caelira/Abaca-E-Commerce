package com.online.abaca.controller;

import com.online.abaca.dto.ProductResponseDTO;
import com.online.abaca.model.Product;
import com.online.abaca.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LinkController {
    private final ProductService productService;

    public LinkController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String showMainPage(Model model){
        List<ProductResponseDTO> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "index";
    }
    @GetMapping("/showLoginPage")
    public String showLoginPage(){
        return "login-page";
    }

    @GetMapping("/showSignupPage")
    public String showSignupPage(){
        return "signup-page";
    }

}
