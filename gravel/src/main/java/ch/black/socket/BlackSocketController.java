package ch.black.socket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BlackSocketController {

    @RequestMapping("/chat")
    public String getBlackSocket() {
        return "chat";
    }

}
