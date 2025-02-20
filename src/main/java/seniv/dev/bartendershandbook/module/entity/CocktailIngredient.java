package seniv.dev.bartendershandbook.module.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

    public CocktailIngredient(
            Cocktail cocktail,
            Ingredient ingredient,
            String amount
    ) {
        this.cocktail = cocktail;
        this.ingredient = ingredient;
        this.amount = amount;
    }
}
