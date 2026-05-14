package com.online.abaca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LinkController {

    @GetMapping("/")
    public String showMainPage(){
        return "index";
    }
}
