package seniv.dev.bartendershandbook.ingredients;

import java.util.Map;

public class IngredientResponseDTO {

    private Long id;
    private String name;
    private Double abv;
    private Category category;
    private String description;
    private Map<String, String> cocktails; // key - name; value - amount

    public IngredientResponseDTO() {
    }

    public IngredientResponseDTO(Long id, String name, Double abv, Category category, String description, Map<String, String> cocktails) {
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

    public Map<String, String> getCocktails() {
        return cocktails;
    }
}
