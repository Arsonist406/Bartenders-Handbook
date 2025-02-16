package seniv.dev.bartendershandbook.module.entity.cocktailIngredient;

import jakarta.persistence.*;
import seniv.dev.bartendershandbook.module.entity.cocktail.Cocktail;
import seniv.dev.bartendershandbook.module.entity.ingredient.Ingredient;

@Entity
@Table(name = "cocktails_ingredients")
public class CocktailIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "cocktail_name",
            referencedColumnName = "name",
            nullable = false
    )
    private Cocktail cocktail;

    @ManyToOne
    @JoinColumn(
            name = "ingredient_name",
            referencedColumnName = "name",
            nullable = false
    )
    private Ingredient ingredient;

    @Column(nullable = false, length = 20)
    private String amount;

    public CocktailIngredient() {}

    public CocktailIngredient(
            Cocktail cocktail,
            Ingredient ingredient,
            String amount
    ) {
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
