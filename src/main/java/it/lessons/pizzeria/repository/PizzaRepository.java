package it.lessons.pizzeria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.lessons.pizzeria.model.Pizza;

public interface PizzaRepository extends JpaRepository<Pizza, Integer>{

    // Select p from Pizza p where UPPER(p.name) = UPPER(?1)
    public List<Pizza> findByNameContainingIgnoreCase(String name);

    // Oppure - query custom
    // @Query(value="SELECT p FROM Pizza where name like ?", nativeQuery=true)
    // public List<Pizza> findByName(String name);
}
