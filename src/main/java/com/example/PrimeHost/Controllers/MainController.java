package com.example.PrimeHost.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {
    // Указывается какой именно юрл адркс обрабатывается  названиесайта/
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Главная страница");
        return "home";
    }

    @GetMapping("/sup")
    public String support(Model model) {
        model.addAttribute("title", "Cтраница поддержки");
        return "support";
    }


}
