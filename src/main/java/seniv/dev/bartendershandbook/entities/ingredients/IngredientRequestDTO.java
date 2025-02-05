package seniv.dev.bartendershandbook.entities.ingredients;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import seniv.dev.bartendershandbook.entities.cocktails_ingredients.CocktailIngredientDTO;
import seniv.dev.bartendershandbook.validation.Create;
import seniv.dev.bartendershandbook.validation.Update;

import java.util.List;

public class IngredientRequestDTO {

    private String name;
    private Double abv;
    private Category category;
    private String description;
    private List<CocktailIngredientDTO> cocktails;

    public IngredientRequestDTO() {
    }

    public IngredientRequestDTO(String name, Double abv, Category category, String description, List<CocktailIngredientDTO> cocktails) {
        this.name = name;
        this.abv = abv;
        this.category = category;
        this.description = description;
        this.cocktails = cocktails;
    }

    public String getName() {
        return name;
    }

    public Double getAbv() {
        return abv;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public List<CocktailIngredientDTO> getCocktails() {
        return cocktails;
    }
}
