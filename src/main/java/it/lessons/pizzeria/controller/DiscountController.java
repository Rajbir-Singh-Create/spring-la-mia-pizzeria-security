package it.lessons.pizzeria.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.lessons.pizzeria.model.Discount;
import it.lessons.pizzeria.repository.DiscountRepository;
import it.lessons.pizzeria.repository.PizzaRepository;
import it.lessons.pizzeria.service.PizzaService;
import jakarta.validation.Valid;





@Controller
@RequestMapping("/discounts")
public class DiscountController {

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private PizzaService pizzaService;

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

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Discount discount = discountRepository.findById(id).get();
        model.addAttribute("discount", discount);
        model.addAttribute("editMode", true);
        
        return "/discounts/edit";
    }

    @PostMapping("/edit/{id}")
    public String editExistent(@Valid @ModelAttribute("discount") Discount formDiscount, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("editMode", false);
            return "/discounts/edit";
        }
        
        double price = formDiscount.getPizza().getPrice();
        double discountApplied = formDiscount.getDiscountPercentage();
        formDiscount.getPizza().setPrice(price-(price*discountApplied/100));

        pizzaRepository.save(formDiscount.getPizza());

        discountRepository.save(formDiscount);

        return "redirect:/pizzas/show/" + formDiscount.getPizza().getId();
    }
    
    // Eliminazione offerta
    @PostMapping("/delete/{discountId}")
    public String delete(@PathVariable Long discountId) {
        // Recupero discount dal repository
        Optional<Discount> discount = discountRepository.findById(discountId);
        
        // Recupero l'id della pizza associata prima di eliminare l'offerta
        Integer pizzaId = discount.get().getPizza().getId();

        // Elimino l'offerta
        discountRepository.deleteById(discountId);
        
        // Redirect al dettaglio pizza
        return "redirect:/pizzas/show/" + pizzaId;
    }
    
}
