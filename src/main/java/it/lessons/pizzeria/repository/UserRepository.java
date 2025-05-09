package it.lessons.pizzeria.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.lessons.pizzeria.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
    
    public Optional<User> findByUsername(String username);
}
