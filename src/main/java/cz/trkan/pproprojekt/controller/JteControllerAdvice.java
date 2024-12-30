package cz.trkan.pproprojekt.controller;

import cz.trkan.pproprojekt.security.MyUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class JteControllerAdvice {
    @ModelAttribute
    public void addUser(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        var x = SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof MyUserDetails;
        if (userDetails instanceof MyUserDetails) {
            model.addAttribute("user", userDetails);
        }
    }
}
