package seniv.dev.bartendershandbook.module.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import seniv.dev.bartendershandbook.module.entity.IngredientCategory;
import seniv.dev.bartendershandbook.validation.group.Create;
import seniv.dev.bartendershandbook.validation.group.Update;

import java.math.BigDecimal;
import java.util.Set;

public record IngredientDTO (

        Long id,

        @NotNull(groups = Create.class, message = "Can't be null")
        @Size(min = 2, max = 50, groups = {Create.class, Update.class}, message = "min=2, max=50 symbols")
        String name,

        @NotNull(groups = Create.class, message = "Can't be null")
        @DecimalMin(value = "0.00", groups = {Create.class, Update.class}, message = "min=0.00")
        @DecimalMax(value = "99.99", groups = {Create.class, Update.class}, message = "max=99.99")
        BigDecimal abv,

        @NotNull(groups = Create.class, message = "Can't be null")
        IngredientCategory category,

        @NotNull(groups = Create.class, message = "Can't be null")
        @Size(max = 2000, groups = {Create.class, Update.class}, message = "max=2000 symbols")
        String description,

        @Valid
        Set<CocktailIngredientDTO> cocktails

) {}
