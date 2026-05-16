package com.online.abaca.controller;

import com.online.abaca.dto.ProductResponseDTO;
import com.online.abaca.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductCatalogController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public String productDetail(@PathVariable("id") Long idProduct, Model model) {
        ProductResponseDTO product = productService.getProductById(idProduct);
        model.addAttribute("product", product);
        return "product-detail";
    }

    @GetMapping("/image/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> getProductImage(@PathVariable("id") Long idProduct) {
        byte[] imageBytes = productService.getProductImage(idProduct);
        if (imageBytes == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageBytes);
    }
}