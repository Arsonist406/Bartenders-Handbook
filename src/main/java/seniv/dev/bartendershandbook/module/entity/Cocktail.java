package seniv.dev.bartendershandbook.module.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
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
    private BigDecimal abv;

    @ManyToMany
    @JoinTable(
            name = "cocktail_glass",
            joinColumns = @JoinColumn(name = "cocktail_name", referencedColumnName = "name"),
            inverseJoinColumns = @JoinColumn(name = "glass_name", referencedColumnName = "name")
    )
    private Set<Glass> glasses;

    @Column(nullable = false, length = 2000)
    private String description;

    @Column(nullable = false, length = 2000)
    private String recipe;

    @OneToMany(
            mappedBy = "cocktail",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    private Set<CocktailIngredient> ingredients;

    public Cocktail(
            String name,
            Integer volume,
            BigDecimal abv,
            Set<Glass> glasses,
            String description,
            String recipe,
            Set<CocktailIngredient> ingredients
    ) {
        this.name = name;
        this.volume = volume;
        this.abv = abv;
        this.glasses = glasses;
        this.description = description;
        this.recipe = recipe;
        this.ingredients = ingredients;
    }
}
