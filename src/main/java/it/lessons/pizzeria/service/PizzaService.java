package it.lessons.pizzeria.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.lessons.pizzeria.model.Discount;
import it.lessons.pizzeria.model.Pizza;
import it.lessons.pizzeria.repository.DiscountRepository;
import it.lessons.pizzeria.repository.IngredientsRepository;
import it.lessons.pizzeria.repository.PizzaRepository;

@Service
public class PizzaService {
    
    // Inietto la repository per utilizzarne i metodi concretizzati dalla IoC
    private DiscountRepository discountRepository;
    private PizzaRepository pizzaRepository;
    private IngredientsRepository ingredientRepository;
    
    @Autowired
    public PizzaService(DiscountRepository discountRepository, PizzaRepository pizzaRepository, IngredientsRepository ingredientRepository) {
        this.discountRepository = discountRepository;
        this.pizzaRepository = pizzaRepository;
        this.ingredientRepository = ingredientRepository;
    }

    // metodo per gestire la ricerca delle pizze tramite nome
    public List<Pizza> findPizzaList(String name){
        List<Pizza> result;

        if(name != null && !name.isBlank()){
            result = pizzaRepository.findByNameContainingIgnoreCase(name);
        } else {
            result = pizzaRepository.findAll();
        }

        return result;
    }

    // metodo per gestire la ricerca delle pizze tramite id
    public Optional<Pizza> findPizzaById(Integer id){
        return pizzaRepository.findById(id);
    }

    // Salvataggio
    public Pizza create(Pizza pizza){
        return pizzaRepository.save(pizza);
    }

    // Modifica
    public Pizza update(Pizza pizza){
        Optional<Pizza> optPizza = pizzaRepository.findById(pizza.getId());
        // cerchiamo se esiste la pizza
        if(optPizza.isEmpty()){
            throw new IllegalArgumentException("impossibile aggiornare la pizza senza l'ID");
        }

        return pizzaRepository.save(pizza);
    }

    // Cancellazione
    public void deletebyId(Integer id){
        Pizza pizza = pizzaRepository.findById(id).get();

        // Cancello tutte le offerte speciali collegate alla pizza
        for(Discount d : pizza.getDiscounts()){
            discountRepository.deleteById(d.getId());
        }
        
        pizzaRepository.deleteById(id);
    }
}
