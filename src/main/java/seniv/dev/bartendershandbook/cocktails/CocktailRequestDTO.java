package seniv.dev.bartendershandbook.cocktails;

import java.util.Map;

public class CocktailRequestDTO {

    private String name;
    private Integer volumeInML; // ml
    private Double abv;
    private String glass;
    private String description;
    private String recipe;
    private Map<String, String> ingredients; // key - name; value - amount

    public CocktailRequestDTO() {}

    public CocktailRequestDTO(String name, Integer volumeInML, Double abv, String glass, String description, String recipe, Map<String, String> ingredients) {
        this.name = name;
        this.volumeInML = volumeInML;
        this.abv = abv;
        this.glass = glass;
        this.description = description;
        this.recipe = recipe;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public Integer getVolumeInML() {
        return volumeInML;
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

    public Map<String, String> getIngredients() {
        return ingredients;
    }
}
