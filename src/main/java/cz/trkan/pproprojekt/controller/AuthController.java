package cz.trkan.pproprojekt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    protected String login() {
        return "login";
    }

    @PostMapping("/login")
    protected String loginPost() {
        return "login";
    }

    @GetMapping("/register")
    protected String register() {
        return "register";
    }
}
