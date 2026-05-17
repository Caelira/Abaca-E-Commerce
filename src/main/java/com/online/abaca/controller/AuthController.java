package com.online.abaca.controller;

import com.online.abaca.dto.UserAccountRequestDTO;
import com.online.abaca.model.Buyer;
import com.online.abaca.model.UserAccount;
import com.online.abaca.repository.BuyerRepository;
import com.online.abaca.repository.UserAccountRepository;
import com.online.abaca.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserAccountRepository userAccountRepository;
    private final BuyerRepository buyerRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String processRegistration(@RequestParam String email,
                                      @RequestParam String password) {

        UserAccount newUser = new UserAccount();
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRole("BUYER");
        userAccountRepository.save(newUser);

        Buyer newBuyer = new Buyer();
        newBuyer.setUserAccount(newUser);
        buyerRepository.save(newBuyer);

        return "redirect:/?registered=true";
    }
}