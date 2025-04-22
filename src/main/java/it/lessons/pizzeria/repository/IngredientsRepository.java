package it.lessons.pizzeria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.lessons.pizzeria.model.Ingredient;

public interface IngredientsRepository extends JpaRepository<Ingredient, Long> {

}
