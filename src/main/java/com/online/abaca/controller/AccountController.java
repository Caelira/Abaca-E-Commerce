package com.online.abaca.controller;

import com.online.abaca.model.UserAccount;
import com.online.abaca.repository.UserAccountRepository;
import com.online.abaca.userdetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/settings")
    public String accountSettings(@AuthenticationPrincipal CustomUserDetails user, Model model) {
        // Fetch the fresh account data from the database
        UserAccount account = userAccountRepository.findById(user.getIdUser()).orElseThrow();
        model.addAttribute("account", account);
        return "account-edit";
    }

    @PostMapping("/update-profile")
    public String updateProfile(
            @RequestParam("fullName") String fullName,
            @RequestParam("email") String email,
            @RequestParam("phoneNumber") String phoneNumber,
            @AuthenticationPrincipal CustomUserDetails user) {

        UserAccount account = userAccountRepository.findById(user.getIdUser()).orElseThrow();
        account.setFullName(fullName);
        account.setEmail(email);
        account.setPhoneNumber(phoneNumber);

        userAccountRepository.save(account);

         CustomUserDetails updatedUserDetails = new CustomUserDetails(account);
        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                updatedUserDetails,
                user.getPassword(),
                updatedUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        return "redirect:/account/settings?profileSuccess=true";
    }

    @PostMapping("/update-password")
    public String updatePassword(
            @RequestParam("newPassword") String newPassword,
            @AuthenticationPrincipal CustomUserDetails user) {

        UserAccount account = userAccountRepository.findById(user.getIdUser()).orElseThrow();
        account.setPassword(passwordEncoder.encode(newPassword));
        userAccountRepository.save(account);

        return "redirect:/account/settings?passwordSuccess=true";
    }
}