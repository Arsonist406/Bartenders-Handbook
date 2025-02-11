package seniv.dev.bartendershandbook.module.cocktails_ingredient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import seniv.dev.bartendershandbook.module.cocktail.Cocktail;
import seniv.dev.bartendershandbook.module.ingredient.Ingredient;

@Entity
@Table(name = "cocktails_ingredients")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class CocktailIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cocktail_id", nullable = false)
    private Cocktail cocktail;

    @ManyToOne
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @Column(nullable = false, length = 20)
    private String amount;

    public CocktailIngredient() {}

    public CocktailIngredient(Cocktail cocktail, Ingredient ingredient, String amount) {
        this.cocktail = cocktail;
        this.ingredient = ingredient;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public Cocktail getCocktail() {
        return cocktail;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public String getAmount() {
        return amount;
    }

    public void setCocktail(Cocktail cocktail) {
        this.cocktail = cocktail;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
