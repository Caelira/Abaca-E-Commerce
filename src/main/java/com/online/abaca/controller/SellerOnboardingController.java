package com.online.abaca.controller;

import com.online.abaca.dto.SellerRequestDTO;
import com.online.abaca.model.Seller;
import com.online.abaca.model.UserAccount;
import com.online.abaca.repository.SellerRepository;
import com.online.abaca.repository.UserAccountRepository;
import com.online.abaca.userdetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SellerOnboardingController {

    private final SellerRepository sellerRepository;
    private final UserAccountRepository userAccountRepository;

    @GetMapping("/start-selling")
    public String showOnboarding() {
        return "start-selling";
    }

    @PostMapping("/start-selling")
    public String processOnboarding(SellerRequestDTO requestDTO, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UserAccount user = userAccountRepository.findById(userDetails.getIdUser()).orElseThrow();

        Seller seller = new Seller();
        seller.setUserAccount(user);
        seller.setStoreName(requestDTO.getStoreName());
        seller.setContactNumber(requestDTO.getContactNumber());
        sellerRepository.save(seller);

        user.setRole("BUYER,SELLER");
        userAccountRepository.save(user);

        CustomUserDetails updatedUserDetails = new CustomUserDetails(user);
        Authentication newAuth = new UsernamePasswordAuthenticationToken(updatedUserDetails, authentication.getCredentials(), updatedUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        return "redirect:/seller/dashboard";
    }
}