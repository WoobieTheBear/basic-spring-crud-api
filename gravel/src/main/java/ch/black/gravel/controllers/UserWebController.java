package ch.black.gravel.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserWebController {

    @GetMapping("/profile")
    public String getPeople() {
        return "user/profile";
    }


}
