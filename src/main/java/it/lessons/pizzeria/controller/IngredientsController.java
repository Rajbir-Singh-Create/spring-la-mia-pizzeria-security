package it.lessons.pizzeria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.lessons.pizzeria.model.Ingredient;
import it.lessons.pizzeria.service.IngredientsService;
import jakarta.validation.Valid;






@Controller
@RequestMapping("/ingredients")
public class IngredientsController {

    @Autowired
    private IngredientsService service;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("list", service.findAllIngredients());
        // va messo un ingredientObj perché l'oggetto deve andare ad inserire i valori nel caso
        // si voglia creare qualcosa, e va dato subito
        model.addAttribute("ingredientObj", new Ingredient());
        return "ingredients/index";
    }
    
    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("ingredientObj") Ingredient ingredient, BindingResult bindingResult) {
        if(!bindingResult.hasErrors()){
            service.create(ingredient);
        }

        return "redirect:/ingredients";
    }
    
    // Delete
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        service.deleteById(id);

        return "redirect:/ingredients";
    }
    

}
