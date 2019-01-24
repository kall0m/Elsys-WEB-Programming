package guessTheHash.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String goToHomePage() {
        return "home";
    }

    @GetMapping("/about")
    public String goToAboutPage() {
        return "about";
    }
}
