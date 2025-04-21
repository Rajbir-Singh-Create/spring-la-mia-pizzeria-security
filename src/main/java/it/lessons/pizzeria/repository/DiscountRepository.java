package it.lessons.pizzeria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.lessons.pizzeria.model.Discount;

public interface DiscountRepository extends JpaRepository<Discount, Long>{

}
