package seniv.dev.bartendershandbook.cocktails;

import seniv.dev.bartendershandbook.cocktails_ingredients.CocktailIngredientDTO;

import java.util.List;

public class CocktailRequestDTO {

    private String name;
    private Integer volume; // ml
    private Double abv;
    private String glass;
    private String description;
    private String recipe;
    private List<CocktailIngredientDTO> ingredients; // key - name; value - amount

    public CocktailRequestDTO() {}

    public CocktailRequestDTO(String name, Integer volume, Double abv, String glass, String description, String recipe, List<CocktailIngredientDTO> ingredients) {
        this.name = name;
        this.volume = volume;
        this.abv = abv;
        this.glass = glass;
        this.description = description;
        this.recipe = recipe;
        this.ingredients = ingredients;
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

    public String getGlass() {
        return glass;
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
