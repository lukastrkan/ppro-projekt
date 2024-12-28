package cz.trkan.pproprojekt.controller;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class JteControllerAdvice {

    @ModelAttribute
    public void csrf(Model model, CsrfToken csrf) {
        model.addAttribute("csrfToken", csrf);
    }

    @ModelAttribute
    public void addUser(Model model) {
        model.addAttribute("user", "user");
    }
}
