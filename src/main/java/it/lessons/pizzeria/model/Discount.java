package it.lessons.pizzeria.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Discount {

    // il valore dell’id deve essere generato automaticamente dal database
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DecimalMin(value="0.0", message="devi inserire un valore valido")
    private double discountPercentage;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDiscount;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDiscount;

    @NotBlank(message="Il titolo è obbligatorio")
    private String title;

    @ManyToOne
    @JoinColumn(name = "pizza_id", nullable = false)
    @JsonBackReference
    private Pizza pizza;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public LocalDate getStartDiscount() {
        return startDiscount;
    }

    public void setStartDiscount(LocalDate startDiscount) {
        this.startDiscount = startDiscount;
    }

    public LocalDate getEndDiscount() {
        return endDiscount;
    }

    public void setEndDiscount(LocalDate endDiscount) {
        this.endDiscount = endDiscount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

}
