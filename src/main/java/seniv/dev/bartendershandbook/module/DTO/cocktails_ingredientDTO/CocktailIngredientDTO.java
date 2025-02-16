package seniv.dev.bartendershandbook.module.DTO.cocktails_ingredientDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import seniv.dev.bartendershandbook.validation.Create;
import seniv.dev.bartendershandbook.validation.Update;

public class CocktailIngredientDTO {

    @NotNull(groups = {Create.class, Update.class}, message = "Can't be null")
    @Size(min = 2, max = 50, groups = {Create.class, Update.class}, message = "Name length min=2, max=50 symbols")
    private String name;

    @NotNull(groups = {Create.class, Update.class}, message = "Can't be null")
    @Size(min = 2, max = 20, groups = {Create.class, Update.class}, message = "Amount length min=2, max=20 symbols")
    private String amount;

    public CocktailIngredientDTO() {}

    public CocktailIngredientDTO(String name, String amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}