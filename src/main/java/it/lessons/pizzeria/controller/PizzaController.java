package it.lessons.pizzeria.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.lessons.pizzeria.model.Pizza;
import it.lessons.pizzeria.repository.PizzaRepository;



@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    // Inietto la repository per utilizzarne i metodi concretizzati dalla IoC
    @Autowired 
    private PizzaRepository pizzaRepository;

    @GetMapping
    public String index(Model model, @RequestParam(name="keyword", required=false) String name) {
        List<Pizza> result;

        if(name != null && !name.isBlank()){
            result = pizzaRepository.findByNameContainingIgnoreCase(name);
        } else {
            result = pizzaRepository.findAll();
        }

        model.addAttribute("list", result);
        return "/pizzas/index";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable("id") Integer id, Model model){
        Optional<Pizza> optPizza = pizzaRepository.findById(id);
        if(optPizza.isPresent()){
            model.addAttribute("pizza", pizzaRepository.findById(id).get());
            return "/pizzas/show";
        }
        
        model.addAttribute("errorCause", "Non esiste nessuna pizza con id " + id);
        model.addAttribute("errorMessage", "Errore di ricerca della pizza");

        return "/error_pages/genericError";
    }
    
    // Metodo GET per la form
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());

        return "/pizzas/create";
    }
    
    // Metodo POST per la form
    @PostMapping("/create")
    public String store(@ModelAttribute("pizza") Pizza formPizza, Model model) {
        // Logica di validazione

        // Logica di salvataggio
        pizzaRepository.save(formPizza);
        return "redirect:/pizzas";
    }
    

}
