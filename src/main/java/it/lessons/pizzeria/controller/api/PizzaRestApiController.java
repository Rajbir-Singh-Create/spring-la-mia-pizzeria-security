package it.lessons.pizzeria.controller.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.lessons.pizzeria.model.Pizza;
import it.lessons.pizzeria.service.PizzaService;





@RestController
@RequestMapping("/api/pizzas")
public class PizzaRestApiController {

    @Autowired
    private PizzaService pizzaService;

    // Lista di pizze - permette anche di filtrare per nome
    @GetMapping
    public ResponseEntity<List<Pizza>> index(@RequestParam(name="keyword", required=false) String name) {
        List<Pizza> pizzas = pizzaService.findPizzaList(name);
        return new ResponseEntity<>(pizzas, HttpStatus.OK);
    }
    
    // Dettagli di una singola pizza (cerca per ID)
    @GetMapping("/{id}")
    public ResponseEntity<Pizza> findById(@PathVariable Integer id) {
        Optional<Pizza> pizza = pizzaService.findPizzaById(id);
        return new ResponseEntity<>(pizza.get(), HttpStatus.OK);
    }
    
    // Creare nuova pizza
    @PostMapping()
    public ResponseEntity<Pizza> create(@RequestBody Pizza entity) {
        Pizza pizza = pizzaService.create(entity);
        return new ResponseEntity<>(pizza, HttpStatus.CREATED);
    }
    
    // Modificare una pizza esistente
    @PutMapping("/{id}")
    public ResponseEntity<Pizza> update(@PathVariable String id, @RequestBody Pizza entity) {
        Pizza updatedPizza = pizzaService.update(entity);
        return new ResponseEntity<>(updatedPizza, HttpStatus.OK);
    }

    // Eliminare una pizza
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        pizzaService.deletebyId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
