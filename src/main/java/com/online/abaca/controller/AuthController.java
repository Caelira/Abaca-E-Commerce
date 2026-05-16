package com.online.abaca.controller;

import com.online.abaca.dto.UserAccountRequestDTO;
import com.online.abaca.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserAccountService userAccountService;

    @PostMapping("/register")
    public String processRegistration(UserAccountRequestDTO requestDTO) {
        userAccountService.createUserAccount(requestDTO);
        return "redirect:/?registered=true";
    }
}