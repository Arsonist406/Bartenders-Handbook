package seniv.dev.bartendershandbook.module.DTO.ingredientDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import seniv.dev.bartendershandbook.module.DTO.cocktails_ingredientDTO.CocktailIngredientDTO;
import seniv.dev.bartendershandbook.module.entity.ingredient.Category;
import seniv.dev.bartendershandbook.validation.Create;
import seniv.dev.bartendershandbook.validation.Update;

import java.util.List;

public class IngredientRequestDTO {

    @NotNull(groups = Create.class, message = "Can't be null")
    @Size(min = 2, max = 50, groups = {Create.class, Update.class}, message = "min=2, max=50 symbols")
    private String name;

    @NotNull(groups = Create.class, message = "Can't be null")
    @DecimalMin(value = "0.00", groups = {Create.class, Update.class}, message = "min=0.00")
    @DecimalMax(value = "99.99", groups = {Create.class, Update.class}, message = "max=99.99")
    private Double abv;

    @NotNull(groups = Create.class, message = "Can't be null")
    //@Pattern(regexp = "SOLID|LIQUID", message = "Invalid category")
    private Category category;

    @NotNull(groups = Create.class, message = "Can't be null")
    @Size(max = 2000, groups = {Create.class, Update.class}, message = "max=2000 symbols")
    private String description;

    @Valid
    //TODO: замінити список - сетом
    private List<CocktailIngredientDTO> cocktails;

    public IngredientRequestDTO() {}

    public IngredientRequestDTO(
            String name,
            Double abv,
            Category category,
            String description,
            List<CocktailIngredientDTO> cocktails
    ) {
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
