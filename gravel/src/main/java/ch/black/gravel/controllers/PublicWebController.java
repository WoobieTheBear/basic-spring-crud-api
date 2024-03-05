package ch.black.gravel.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicWebController {

    @GetMapping("/")
    public String showLandingPage() {
        return "home";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "access-denied";
    }

}
