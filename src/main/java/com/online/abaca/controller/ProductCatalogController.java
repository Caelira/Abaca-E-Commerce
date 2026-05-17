package com.online.abaca.controller;

import com.online.abaca.dto.ProductResponseDTO;
import com.online.abaca.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductCatalogController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public String productDetail(@PathVariable("id") Long idProduct, Model model) {
        ProductResponseDTO product = productService.getProductById(idProduct);
        model.addAttribute("product", product);

        Page<ProductResponseDTO> recommendations = productService.getDailyDiscoverFeed(0, 12);
        model.addAttribute("recommendedProducts", recommendations.getContent());

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