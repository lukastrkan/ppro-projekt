package cz.trkan.pproprojekt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemeController {
    @GetMapping("/")
    protected String home()
    {
        return "";
    }

    @GetMapping("/403")
    protected String unauthorized(){
        return "unauthorized";
    }
}
