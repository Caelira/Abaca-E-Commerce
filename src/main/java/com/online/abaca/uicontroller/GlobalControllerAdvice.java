package com.online.abaca.uicontroller;

import com.online.abaca.repository.CartItemRepository;
import com.online.abaca.userdetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    private final CartItemRepository cartItemRepository;

    @ModelAttribute("currentUser")
    public CustomUserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            return (CustomUserDetails) authentication.getPrincipal();
        }
        return null;
    }

    @ModelAttribute("cartCount")
    public Integer getCartCount(@ModelAttribute("currentUser") CustomUserDetails currentUser) {
        if (currentUser != null && currentUser.getRole().contains("BUYER")) {
            Integer count = cartItemRepository.sumQuantityByUserId(currentUser.getIdUser());
            return count != null ? count : 0;
        }
        return 0;
    }
}