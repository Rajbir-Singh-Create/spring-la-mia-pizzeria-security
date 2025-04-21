package it.lessons.pizzeria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.lessons.pizzeria.model.Discount;
import it.lessons.pizzeria.repository.DiscountRepository;
import it.lessons.pizzeria.repository.PizzaRepository;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/discounts")
public class DiscountController {

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("discount") Discount formDiscount, BindingResult bindingResult, Model model) {
        
        if(bindingResult.hasErrors()){
            model.addAttribute("editMode", false);
            return "/discounts/edit";
        }

        double price = formDiscount.getPizza().getPrice();
        double discountApplied = formDiscount.getDiscountPercentage();
        formDiscount.getPizza().setPrice(price-(price*discountApplied/100));

        pizzaRepository.save(formDiscount.getPizza());

        discountRepository.save(formDiscount);
        
        // torniamo allo show delle pizze
        // concateniamo la stringa: redirect... + l'id della pizza passando dal formDiscount
        // formDiscount è la form che viene passata dalla edit (form)
        return "redirect:/pizzas/show/" + formDiscount.getPizza().getId();
    }
    
}
