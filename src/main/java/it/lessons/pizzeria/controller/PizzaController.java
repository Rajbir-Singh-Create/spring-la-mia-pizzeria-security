package it.lessons.pizzeria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.lessons.pizzeria.model.Pizza;
import it.lessons.pizzeria.repository.PizzaRepository;


@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    // Inietto la repository per utilizzarne i metodi concretizzati dalla IoC
    @Autowired 
    private PizzaRepository pizzaRepository;

    @GetMapping
    public String index(Model model) {
        List<Pizza> result = pizzaRepository.findAll();
        model.addAttribute("list", result);
        
        return "/pizzas/index";
    }
    
}
