package seniv.dev.bartendershandbook.module.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String name;

    @Column(nullable = false, precision = 5, scale = 2)
    @DecimalMin("0.00")
    @DecimalMax("99.99")
    private BigDecimal abv;

    @Enumerated(EnumType.STRING)
    private IngredientCategory category;

    @Column(nullable = false, length = 2000)
    private String description;

    @OneToMany(
            mappedBy = "ingredient",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    private Set<CocktailIngredient> cocktails;

    public Ingredient(
            String name,
            BigDecimal abv,
            IngredientCategory category,
            String description,
            Set<CocktailIngredient> cocktails
    ) {
        this.name = name;
        this.abv = abv;
        this.category = category;
        this.description = description;
        this.cocktails = cocktails;
    }
}

