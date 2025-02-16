package seniv.dev.bartendershandbook.module.DTO.cocktailDTO;

import seniv.dev.bartendershandbook.module.DTO.cocktails_ingredientDTO.CocktailIngredientDTO;

import java.util.List;
import java.util.Set;

public class CocktailResponseDTO {

    private Long id;
    private String name;
    private Integer volume; // ml
    private Double abv;
    private Set<String> glasses;
    private String description;
    private String recipe;
    //TODO: замінити список - сетом
    private List<CocktailIngredientDTO> ingredients;

    public CocktailResponseDTO() {}

    public CocktailResponseDTO(
            Long id,
            String name,
            Integer volume,
            Double abv,
            Set<String> glasses,
            String description,
            String recipe,
            List<CocktailIngredientDTO> ingredients
    ) {
        this.id = id;
        this.name = name;
        this.volume = volume;
        this.abv = abv;
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
        return abv;
    }

    public Set<String> getGlasses() {
        return glasses;
    }

    public String getDescription() {
        return description;
    }

    public String getRecipe() {
        return recipe;
    }

    public List<CocktailIngredientDTO> getIngredients() {
        return ingredients;
    }
}
