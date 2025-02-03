package seniv.dev.bartendershandbook.ingredients;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import seniv.dev.bartendershandbook.cocktails_ingredients.CocktailIngredient;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ingredients")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String name;

    @Column(precision = 5, scale = 2, nullable = false)
    @DecimalMin("0.00")
    @DecimalMax("99.99")
    private BigDecimal abv;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(length = 2000)
    private String description;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnore
    private List<CocktailIngredient> cocktails = new ArrayList<>();

    public Ingredient() {
    }

    public Ingredient(Long id, String name, Double abv, Category category, String description, List<CocktailIngredient> cocktails) {
        this.id = id;
        this.name = name;
        this.abv = BigDecimal.valueOf(abv);
        this.category = category;
        this.description = description;
        this.cocktails = cocktails;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getAbv() {
        return Double.valueOf(String.valueOf(abv));
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public List<CocktailIngredient> getCocktails() {
        return cocktails;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAbv(Double abv) {
        this.abv = BigDecimal.valueOf(abv);
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCocktails(List<CocktailIngredient> cocktails) {
        this.cocktails = cocktails;
    }
}

