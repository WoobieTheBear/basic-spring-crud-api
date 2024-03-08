package ch.black.util.security.auth.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginWebController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "security/login";
    }

    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "security/access-denied";
    }

}
