package ch.black.gravel.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BlackRestController {
    @GetMapping("/test")
    public String sendBasicResponse(){
        return "Response generated by BlackRestController";
    }
}
