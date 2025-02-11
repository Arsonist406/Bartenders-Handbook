package seniv.dev.bartendershandbook.module.ingredientDTO;

import seniv.dev.bartendershandbook.module.cocktails_ingredientDTO.CocktailIngredientDTO;
import seniv.dev.bartendershandbook.module.ingredient.Category;

import java.util.ArrayList;
import java.util.List;

public class IngredientResponseDTO {

    private Long id;
    private String name;
    private Double abv;
    private Category category;
    private String description;
    private List<CocktailIngredientDTO> cocktails = new ArrayList<>();

    public IngredientResponseDTO() {
    }

    public IngredientResponseDTO(Long id, String name, Double abv, Category category, String description, List<CocktailIngredientDTO> cocktails) {
        this.id = id;
        this.name = name;
        this.abv = abv;
        this.category = category;
        this.description = description;
        this.cocktails = cocktails;
    }

    public Long getId() {
        return id;
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
