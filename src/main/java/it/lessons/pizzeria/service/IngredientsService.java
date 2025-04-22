package it.lessons.pizzeria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.lessons.pizzeria.model.Ingredient;
import it.lessons.pizzeria.model.Pizza;
import it.lessons.pizzeria.repository.IngredientsRepository;

@Service
public class IngredientsService {

    @Autowired
    private IngredientsRepository ingredientRepository;

    public List<Ingredient> findAllIngredients(){
        return ingredientRepository.findAll();
    }

    public Ingredient create(Ingredient ing){
        return ingredientRepository.save(ing);
    }

    public void deleteById(Long id){
        Ingredient ing = ingredientRepository.findById(id).get();
        for(Pizza p : ing.getPizzas()){
            p.getIngredients().remove(ing);
        }

        ingredientRepository.deleteById(id);
    }
}
