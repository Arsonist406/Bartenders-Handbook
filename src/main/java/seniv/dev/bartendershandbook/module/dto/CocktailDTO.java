package seniv.dev.bartendershandbook.module.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import seniv.dev.bartendershandbook.validation.group.Create;
import seniv.dev.bartendershandbook.validation.group.Update;

import java.math.BigDecimal;
import java.util.Set;

public record CocktailDTO (

        Long id,

        @NotNull(groups = Create.class, message = "Can't be null")
        @Size(min = 2, max = 50, groups = {Create.class, Update.class}, message = "min=2, max=50 symbols")
        String name,

        @NotNull(groups = Create.class, message = "Can't be null")
        @Min(value = 1, groups = {Create.class, Update.class}, message = "min=1" )
        Integer volume, // ml

        @NotNull(groups = Create.class, message = "Can't be null")
        @DecimalMin(value = "0.00", groups = {Create.class, Update.class}, message = "min=0.00")
        @DecimalMax(value = "99.99", groups = {Create.class, Update.class}, message = "max=99.99")
        BigDecimal abv,

        @NotNull(groups = Create.class, message = "Can't be null")
        Set<String> glasses,

        @NotNull(groups = Create.class, message = "Can't be null")
        @Size(max = 2000, groups = {Create.class, Update.class}, message = "max=2000 symbols")
        String description,

        @NotNull(groups = Create.class, message = "Can't be null")
        @Size(max = 2000, groups = {Create.class, Update.class}, message = "max=2000 symbols")
        String recipe,

        @Valid
        @NotNull(groups = Create.class, message = "Can't be null")
        Set<CocktailIngredientDTO> ingredients

) {}