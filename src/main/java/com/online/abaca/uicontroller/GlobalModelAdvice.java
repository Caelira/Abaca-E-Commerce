package com.online.abaca.uicontroller;

import com.online.abaca.repository.CartHeadRepository;
import com.online.abaca.repository.CartItemRepository;
import com.online.abaca.userdetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalModelAdvice {

    private final CartItemRepository cartItemRepository;
    private final CartHeadRepository cartHeadRepository;

    @ModelAttribute("currentUser")
    public CustomUserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof CustomUserDetails) {
            return (CustomUserDetails) authentication.getPrincipal();
        }
        return null;
    }

    @ModelAttribute("cartCount")
    public int getCartCount() {
        CustomUserDetails user = getCurrentUser();
        if (user != null && "BUYER".equalsIgnoreCase(user.getRole())) {
            return cartHeadRepository.findByBuyer_UserAccount_IdUserAndStatus(user.getIdUser(), "ACTIVE")
                    .map(cartHead -> cartItemRepository.countByCartHead_IdCart(cartHead.getIdCart()))
                    .orElse(0L).intValue();
        }
        return 0;
    }
}