package it.lessons.pizzeria.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.lessons.pizzeria.model.Pizza;


@Controller
@RequestMapping("/pizza")
public class PizzaController {

    @GetMapping("path")
    public String index(@RequestParam String param, Model model) {
        List<Pizza> result = new ArrayList<>();
        model.addAttribute("list", result);
        
        return "/pizza/index";
    }
    
}
