package com.online.abaca.controller;

import com.online.abaca.model.UserAccount;
import com.online.abaca.repository.UserAccountRepository;
import com.online.abaca.service.AddressService;
import com.online.abaca.userdetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AddressService addressService;

    @GetMapping("/settings")
    public String accountSettings(@AuthenticationPrincipal CustomUserDetails user, Model model) {
        UserAccount account = userAccountRepository.findById(user.getIdUser()).orElseThrow();
        model.addAttribute("account", account);
        return "account-edit";
    }

    @GetMapping("/addresses")
    public String manageAddresses(@AuthenticationPrincipal CustomUserDetails user, Model model) {
        model.addAttribute("addresses", addressService.getAddressesByUserId(user.getIdUser()));
        return "account-addresses";
    }

    @PostMapping("/update-profile")
    public String updateProfile(
            @RequestParam("fullName") String fullName,
            @RequestParam("email") String email,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
            @AuthenticationPrincipal CustomUserDetails user) {

        try {
            UserAccount account = userAccountRepository.findById(user.getIdUser()).orElseThrow();
            account.setFullName(fullName);
            account.setEmail(email);
            account.setPhoneNumber(phoneNumber);

            if (profileImage != null && !profileImage.isEmpty()) {
                account.setUserProfile(profileImage.getBytes());
            }

            userAccountRepository.save(account);

            CustomUserDetails updatedUserDetails = new CustomUserDetails(account);
            Authentication newAuth = new UsernamePasswordAuthenticationToken(
                    updatedUserDetails,
                    user.getPassword(),
                    updatedUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(newAuth);

            return "redirect:/account/settings?profileSuccess=true";
        } catch (Exception e) {
            return "redirect:/account/settings?error=true";
        }
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

    @GetMapping("/profile-image/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> getProfileImage(@PathVariable("id") Long idUser) {
        UserAccount account = userAccountRepository.findById(idUser).orElse(null);
        if (account == null || account.getUserProfile() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(account.getUserProfile());
    }
}