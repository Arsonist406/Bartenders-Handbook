package seniv.dev.bartendershandbook.module.DTO.cocktailDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import seniv.dev.bartendershandbook.module.DTO.cocktails_ingredientDTO.CocktailIngredientDTO;
import seniv.dev.bartendershandbook.validation.Create;
import seniv.dev.bartendershandbook.validation.Update;

import java.util.List;
import java.util.Set;

public class CocktailRequestDTO {

    @NotNull(groups = Create.class, message = "Can't be null")
    @Size(min = 2, max = 50, groups = {Create.class, Update.class}, message = "min=2, max=50 symbols")
    private String name;

    @NotNull(groups = Create.class, message = "Can't be null")
    @Min(value = 1, groups = {Create.class, Update.class}, message = "min=1" )
    private Integer volume; // ml

    @NotNull(groups = Create.class, message = "Can't be null")
    @DecimalMin(value = "0.00", groups = {Create.class, Update.class}, message = "min=0.00")
    @DecimalMax(value = "99.99", groups = {Create.class, Update.class}, message = "max=99.99")
    private Double abv;

    @NotNull(groups = Create.class, message = "Can't be null")
    private Set<String> glasses;

    @NotNull(groups = Create.class, message = "Can't be null")
    @Size(max = 2000, groups = {Create.class, Update.class}, message = "max=2000 symbols")
    private String description;

    @NotNull(groups = Create.class, message = "Can't be null")
    @Size(max = 2000, groups = {Create.class, Update.class}, message = "max=2000 symbols")
    private String recipe;

    @Valid
    @NotNull(groups = Create.class, message = "Can't be null")
    //TODO: замінити список - сетом
    private List<CocktailIngredientDTO> ingredients;

    public CocktailRequestDTO() {}

    public CocktailRequestDTO(
            String name,
            Integer volume,
            Double abv,
            Set<String> glasses,
            String description,
            String recipe,
            List<CocktailIngredientDTO> ingredients
    ) {
        this.name = name;
        this.volume = volume;
        this.abv = abv;
        this.glasses = glasses;
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
