package com.online.abaca.controller;

import com.online.abaca.model.Buyer;
import com.online.abaca.model.UserAccount;
import com.online.abaca.repository.BuyerRepository;
import com.online.abaca.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.regex.Pattern;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserAccountRepository userAccountRepository;
    private final BuyerRepository buyerRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[0-9\\W]).{8,}$");

    @PostMapping("/register")
    public String processRegistration(@RequestParam String email,
                                      @RequestParam String password) {

        if (userAccountRepository.findByEmail(email).isPresent()) {
            return "redirect:/?error=Email+address+is+already+registered.";
        }

        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            return "redirect:/?error=Password+must+be+at+least+8+characters+long+and+contain+at+least+one+number+or+special+character.";
        }

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