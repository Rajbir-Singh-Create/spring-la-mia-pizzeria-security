package it.lessons.pizzeria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.lessons.pizzeria.model.Pizza;

public interface PizzaRepository extends JpaRepository<Pizza, Integer>{
    
}
