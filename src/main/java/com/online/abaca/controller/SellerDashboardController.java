package com.online.abaca.controller;

import com.online.abaca.dto.ProductRequestDTO;
import com.online.abaca.dto.ProductResponseDTO;
import com.online.abaca.model.OrderHead;
import com.online.abaca.model.OrderItem;
import com.online.abaca.model.Seller;
import com.online.abaca.repository.OrderHeadRepository;
import com.online.abaca.repository.OrderItemRepository;
import com.online.abaca.repository.SellerRepository;
import com.online.abaca.service.CategoryService;
import com.online.abaca.service.ProductService;
import com.online.abaca.userdetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/seller")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SellerDashboardController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final SellerRepository sellerRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderHeadRepository orderHeadRepository;
    private Seller getCurrentSeller(CustomUserDetails user) {
        return sellerRepository.findByUserAccount_IdUser(user.getIdUser())
                .orElseThrow(() -> new RuntimeException("Seller profile not found"));
    }

    @Transactional(readOnly = true)
    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal CustomUserDetails user, Model model) {
        Seller seller = sellerRepository.findByUserAccount_IdUser(user.getIdUser()).orElse(null);
        if (seller == null) {
            return "redirect:/start-selling";
        }

        List<OrderItem> items = orderItemRepository.findByProduct_Seller_IdSellerOrderByOrderHead_OrderDateDesc(seller.getIdSeller());

        BigDecimal totalSales = items.stream()
                .filter(item -> "COMPLETED".equals(item.getOrderHead().getOrderStatus()))
                .map(item -> item.getFinalUnitPrice().multiply(new java.math.BigDecimal(item.getOrderQuantity())))
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);

        model.addAttribute("seller", seller);
        model.addAttribute("items", items);
        model.addAttribute("totalSales", totalSales);
        model.addAttribute("totalOrders", items.size());

        return "seller-dashboard";
    }

    @GetMapping("/products")
    public String products(@AuthenticationPrincipal CustomUserDetails user, Model model) {
        Seller seller = getCurrentSeller(user);
        model.addAttribute("products", productService.getProductsBySellerId(seller.getIdSeller()));
        return "seller-products";
    }

    @GetMapping("/products/add")
    public String showAddProduct(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "seller-product-add";
    }

    @PostMapping("/products/add")
    public String processAddProduct(
            @RequestParam("productName") String productName,
            @RequestParam("productPrice") BigDecimal productPrice,
            @RequestParam(value = "originalPrice", required = false) BigDecimal originalPrice,
            @RequestParam("stockQuantity") Integer stockQuantity,
            @RequestParam("idCategory") Long idCategory,
            @RequestParam("imageFile") MultipartFile imageFile,
            @AuthenticationPrincipal CustomUserDetails user) {

        try {
            Seller seller = getCurrentSeller(user);

            ProductRequestDTO dto = new ProductRequestDTO();
            dto.setIdSeller(seller.getIdSeller());
            dto.setIdCategory(idCategory);
            dto.setProductName(productName);
            dto.setProductPrice(productPrice);
            dto.setOriginalPrice(originalPrice);
            dto.setStockQuantity(stockQuantity);

            if (!imageFile.isEmpty()) {
                dto.setProductImage(imageFile.getBytes());
            }

            productService.createProduct(dto);
            return "redirect:/seller/products?success=true";

        } catch (Exception e) {
            return "redirect:/seller/products/add?error=true";
        }
    }

    @GetMapping("/orders")
    public String orders(@AuthenticationPrincipal CustomUserDetails user, Model model) {
        Seller seller = getCurrentSeller(user);
        List<OrderItem> orderedItems = orderItemRepository.findByProduct_Seller_IdSellerOrderByOrderHead_OrderDateDesc(seller.getIdSeller());
        model.addAttribute("orderedItems", orderedItems);
        return "seller-orders";
    }
    @PostMapping("/orders/ship")
    public String shipOrder(@RequestParam("orderId") Long orderId) {
        OrderHead order = orderHeadRepository.findById(orderId).orElseThrow();
        order.setOrderStatus("SHIPPED");
        orderHeadRepository.save(order);

        return "redirect:/seller/orders?shipped=true";
    }
    @GetMapping("/products/edit/{id}")
    public String showEditProduct(@PathVariable("id") Long idProduct, @AuthenticationPrincipal CustomUserDetails user, Model model) {
        Seller seller = getCurrentSeller(user);
        ProductResponseDTO product = productService.getProductById(idProduct);

        if (!product.getIdSeller().equals(seller.getIdSeller())) return "redirect:/seller/products";

        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "seller-product-edit";
    }

    @PostMapping("/products/edit/{id}")
    public String processEditProduct(
            @PathVariable("id") Long idProduct,
            @ModelAttribute ProductRequestDTO requestDTO,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @AuthenticationPrincipal CustomUserDetails user) {
        try {
            Seller seller = getCurrentSeller(user);
            productService.updateProduct(idProduct, seller.getIdSeller(), requestDTO, imageFile);
            return "redirect:/seller/products?updated=true";
        } catch (Exception e) {
            return "redirect:/seller/products?error=true";
        }
    }

    // Delete
    @PostMapping("/products/delete")
    public String deleteProduct(@RequestParam("productId") Long productId, @AuthenticationPrincipal CustomUserDetails user) {
        Seller seller = getCurrentSeller(user);
        productService.deleteProduct(productId, seller.getIdSeller());
        return "redirect:/seller/products?deleted=true";
    }
    @GetMapping("/profile")
    public String viewProfile(@AuthenticationPrincipal CustomUserDetails user, Model model) {
        Seller seller = getCurrentSeller(user);
        model.addAttribute("seller", seller);
        return "seller-profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam("storeName") String storeName, @RequestParam("contactNumber") String contactNumber, @AuthenticationPrincipal CustomUserDetails user) {
        Seller seller = getCurrentSeller(user);
        seller.setStoreName(storeName);
        seller.setContactNumber(contactNumber);
        sellerRepository.save(seller);
        return "redirect:/seller/profile?success=true";
    }
}