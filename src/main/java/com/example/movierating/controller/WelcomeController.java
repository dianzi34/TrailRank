package com.example.movierating.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "welcome";
    }
}
