package it.lessons.pizzeria.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.lessons.pizzeria.model.Discount;
import it.lessons.pizzeria.model.Pizza;
import it.lessons.pizzeria.service.IngredientsService;
import it.lessons.pizzeria.service.PizzaService;
import jakarta.validation.Valid;





@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    @Autowired
    private PizzaService service;

    @Autowired
    private IngredientsService ingService;

    // Index - home page
    @GetMapping
    public String index(Model model, @RequestParam(name="keyword", required=false) String name) {
        model.addAttribute("list", service.findPizzaList(name));
        return "/pizzas/index";
    }

    // show page
    @GetMapping("/show/{id}")
    public String show(@PathVariable("id") Integer id, Model model){
        Optional<Pizza> optPizza = service.findPizzaById(id);
        if(optPizza.isPresent()){
            model.addAttribute("pizza", optPizza.get());
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
        // riempiamo la lista degli ingredienti (delle checkbox per gli ingredienti)
        model.addAttribute("ingredientsList", ingService.findAllIngredients());
        return "/pizzas/create";
    }
    
    // Metodo POST per la form - creazione pizza
    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // Logica di validazione
        if(bindingResult.hasErrors()){
            // ricarico gli ingredienti
            model.addAttribute("ingredientsList", ingService.findAllIngredients());
            // Se il BindingResult ha dato errori
            // ritorno alla pagina create = resto nella pagina
            return "/pizzas/create";
        }

        // Logica di salvataggio
        service.create(formPizza);

        redirectAttributes.addFlashAttribute("successMessage", "Pizza created successfully!");
        return "redirect:/pizzas";
    }
    
    // UPDATE
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model){
        model.addAttribute("pizza", service.findPizzaById(id).get());
        model.addAttribute("ingredientsList", ingService.findAllIngredients());

        return "/pizzas/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // Validazione custom di errori
        Pizza dbPizza = service.findPizzaById(formPizza.getId()).get();
        
        if(!dbPizza.getName().equals(formPizza.getName())){
            bindingResult.addError(new ObjectError("name", "stai modificando il nome"));
        }

        if(bindingResult.hasErrors()){
            return "/pizzas/edit";
        }
        
        service.create(formPizza);

        redirectAttributes.addFlashAttribute("successMessage", "Pizza updated successfully!");
        return "redirect:/pizzas";
    }
    
    // DELETE
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.deletebyId(id);
        
        return "redirect:/pizzas";
    }
    
    // Metodo GET per la form di una nuova offerta
    @GetMapping("/{id}/discounts")
    public String discount(@PathVariable Integer id, Model model) {
        // Creiamo un offerta vuota ma gli passiamo la pizza di riferimento
        Discount discount = new Discount();
        discount.setPizza(service.findPizzaById(id).get());

        model.addAttribute("discount", discount);
        model.addAttribute("editMode", false);

        return "/discounts/edit";
    }
        
}
