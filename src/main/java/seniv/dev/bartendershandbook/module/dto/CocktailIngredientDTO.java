package seniv.dev.bartendershandbook.module.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import seniv.dev.bartendershandbook.validation.group.CreateGroup;
import seniv.dev.bartendershandbook.validation.group.UpdateGroup;

public record CocktailIngredientDTO (

        @NotNull(groups = {CreateGroup.class, UpdateGroup.class}, message = "Can't be null")
        @Size(min = 2, max = 50, groups = {CreateGroup.class, UpdateGroup.class}, message = "min=2, max=50 symbols")
        String name,

        @NotNull(groups = {CreateGroup.class, UpdateGroup.class}, message = "Can't be null")
        @Size(max = 20, groups = {CreateGroup.class, UpdateGroup.class}, message = "max=20 symbols")
        String amount

) {}