package seniv.dev.bartendershandbook.ingredients;

import java.math.BigDecimal;
import java.util.Map;

public class IngredientRequestDTO {

    private String name;
    private Double abv;
    private Category category;
    private String description;
    private Map<String, String> cocktails; // key - name; value - amount

    public IngredientRequestDTO() {
    }

    public IngredientRequestDTO(String name, Double abv, Category category, String description, Map<String, String> cocktails) {
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

    public Map<String, String> getCocktails() {
        return cocktails;
    }
}
