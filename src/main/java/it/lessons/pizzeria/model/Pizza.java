package it.lessons.pizzeria.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// Oggetto che mappa la tabella del DB

@Entity
@Table(name = "pizzas")
public class Pizza {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank(message="Il nome è obbligatorio")
    @Size(max=50, message="hai superato il numero di caratteri possibili")
    private String name;

    @NotBlank(message="Inserisci una descrizione")
    @Size(max=500, message="hai superato il numero di caratteri possibili")
    private String description;

    @NotBlank(message="Inserisci una foto")
    private String picture;
    
    @NotNull(message="Il prezzo è obbligatorio")
    @Min(value=1)
    @DecimalMin(value="0.0", message="devi inserire un prezzo valido")
    private Double price;

    @OneToMany(mappedBy="pizza")
    private List<Discount> discounts;

    @ManyToMany()
    @JoinTable(
        name = "pizza_ingredients",
        joinColumns = @JoinColumn(name="pizza_id"),
        inverseJoinColumns=@JoinColumn(name="ingredient_id")
    )
    private List<Ingredient> ingredients;

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Discount> discounts) {
        this.discounts = discounts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Double getPrice() {
        return price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    
}
