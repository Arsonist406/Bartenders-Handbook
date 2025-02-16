package seniv.dev.bartendershandbook.module.entity.cocktail;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;
import seniv.dev.bartendershandbook.module.entity.cocktailIngredient.CocktailIngredient;
import seniv.dev.bartendershandbook.module.entity.glass.Glass;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

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
    private BigDecimal abv;

    @ManyToMany
    @JoinTable(
            name = "cocktails_glasses",
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
    //TODO: замінити список - сетом
    private List<CocktailIngredient> ingredients;

    public Cocktail() {}

    public Cocktail(
            String name,
            Integer volume,
            Double abv,
            Set<Glass> glasses,
            String description,
            String recipe,
            List<CocktailIngredient> ingredients
    ) {
        this.name = name;
        this.volume = volume;
        this.abv = BigDecimal.valueOf(abv);
        this.glasses = glasses;
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

    public Set<Glass> getGlasses() {
        return glasses;
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

    public void setGlasses(Set<Glass> glass) {
        this.glasses = glass;
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
