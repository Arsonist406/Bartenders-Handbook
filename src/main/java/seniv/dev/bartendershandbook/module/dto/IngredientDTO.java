package seniv.dev.bartendershandbook.module.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import seniv.dev.bartendershandbook.module.entity.IngredientCategory;
import seniv.dev.bartendershandbook.validation.group.CreateGroup;
import seniv.dev.bartendershandbook.validation.group.UpdateGroup;

import java.math.BigDecimal;
import java.util.Set;

public record IngredientDTO (

        @Null(groups = {CreateGroup.class, UpdateGroup.class}, message = "Must be null")
        Long id,

        @NotNull(groups = CreateGroup.class, message = "Can't be null")
        @Size(min = 2, max = 50, groups = {CreateGroup.class, UpdateGroup.class}, message = "min=2, max=50 symbols")
        String name,

        @NotNull(groups = CreateGroup.class, message = "Can't be null")
        @DecimalMin(value = "0.00", groups = {CreateGroup.class, UpdateGroup.class}, message = "min=0.00")
        @DecimalMax(value = "99.99", groups = {CreateGroup.class, UpdateGroup.class}, message = "max=99.99")
        BigDecimal abv,

        @NotNull(groups = CreateGroup.class, message = "Can't be null")
        IngredientCategory category,

        @NotNull(groups = CreateGroup.class, message = "Can't be null")
        @Size(max = 2000, groups = {CreateGroup.class, UpdateGroup.class}, message = "max=2000 symbols")
        String description,

        @Valid
        Set<CocktailIngredientDTO> cocktails

) {}
