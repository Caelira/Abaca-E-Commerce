package com.online.abaca.uicontroller;

import com.online.abaca.dto.ProductResponseDTO;
import com.online.abaca.model.Buyer;
import com.online.abaca.model.CartHead;
import com.online.abaca.model.CartItem;
import com.online.abaca.repository.BuyerRepository;
import com.online.abaca.repository.CartHeadRepository;
import com.online.abaca.repository.CartItemRepository;
import com.online.abaca.service.CartItemService;
import com.online.abaca.service.ProductService;
import com.online.abaca.userdetails.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartItemService cartItemService;
    private final BuyerRepository buyerRepository;
    private final CartHeadRepository cartHeadRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;

    @GetMapping
    public String viewCart(@AuthenticationPrincipal CustomUserDetails user, Model model) {
        Buyer buyer = buyerRepository.findByUserAccount_IdUser(user.getIdUser())
                .orElseThrow(() -> new RuntimeException("Buyer profile not found"));

        Optional<CartHead> activeCartOpt = cartHeadRepository.findByBuyer_IdBuyerAndStatus(buyer.getIdBuyer(), "ACTIVE");

        if (activeCartOpt.isPresent()) {
            CartHead activeCart = activeCartOpt.get();
            List<CartItem> cartItems = cartItemRepository.findAllByCartHead_IdCart(activeCart.getIdCart());
            model.addAttribute("cartItems", cartItems);

            BigDecimal cartTotal = cartItems.stream()
                    .map(item -> item.getProduct().getProductPrice().multiply(new BigDecimal(item.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            model.addAttribute("cartTotal", cartTotal);
        } else {
            model.addAttribute("cartItems", List.of());
            model.addAttribute("cartTotal", BigDecimal.ZERO);
        }

        Page<ProductResponseDTO> recommendations = productService.getDailyDiscoverFeed(0, 12);
        model.addAttribute("recommendedProducts", recommendations.getContent());

        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(
            @RequestParam("productId") Long productId,
            @RequestParam("quantity") Integer quantity,
            @RequestParam(value = "action", required = false, defaultValue = "add") String action,
            @AuthenticationPrincipal CustomUserDetails user) {
        try {
            cartItemService.addItemToCart(user.getIdUser(), productId, quantity);
            if ("buy".equals(action)) return "redirect:/cart";
            return "redirect:/products/" + productId + "?added=true";
        } catch (Exception e) {
            return "redirect:/products/" + productId + "?error=" + e.getMessage();
        }
    }

    @PostMapping("/remove")
    public String removeCartItem(@RequestParam("cartItemId") Long cartItemId, @AuthenticationPrincipal CustomUserDetails user) {
        cartItemService.removeCartItem(user.getIdUser(), cartItemId);
        return "redirect:/cart";
    }
}