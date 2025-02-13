package seniv.dev.bartendershandbook.module.cocktail;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;
import seniv.dev.bartendershandbook.module.cocktails_ingredient.CocktailIngredient;
import seniv.dev.bartendershandbook.module.glass.Glass;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cocktails")
public class Cocktail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String name;

    @Column(nullable = false)
    @Positive
    private Integer volume; // ml

    @Column(precision = 5, scale = 2, nullable = false)
    @DecimalMin("0.00")
    @DecimalMax("99.99")
    private BigDecimal abv = BigDecimal.valueOf(0.00);

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "glass_id", referencedColumnName = "id", nullable = false)
    private Glass glass;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false, length = 2000)
    private String recipe;

    @OneToMany(mappedBy = "cocktail", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<CocktailIngredient> ingredients = new ArrayList<>();

    public Cocktail() {}

    public Cocktail(String name, Integer volume, Double abv, Glass glass, String description, String recipe, List<CocktailIngredient> ingredients) {
        this.name = name;
        this.volume = volume;
        this.abv = BigDecimal.valueOf(abv);
        this.glass = glass;
        this.description = description;
        this.recipe = recipe;
        this.ingredients = ingredients;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getVolume() {
        return volume;
    }

    public Double getAbv() {
        return Double.valueOf(String.valueOf(abv));
    }

    public Glass getGlass() {
        return glass;
    }

    public String getDescription() {
        return description;
    }

    public String getRecipe() {
        return recipe;
    }

    public List<CocktailIngredient> getIngredients() {
        return ingredients;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public void setAbv(Double abv) {
        this.abv = BigDecimal.valueOf(abv);
    }

    public void setGlass(Glass glass) {
        this.glass = glass;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public void setIngredients(List<CocktailIngredient> cocktailIngredients) {
        this.ingredients = cocktailIngredients;
    }
}
