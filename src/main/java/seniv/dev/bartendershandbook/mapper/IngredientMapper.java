package seniv.dev.bartendershandbook.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import seniv.dev.bartendershandbook.module.dto.CocktailIngredientDTO;
import seniv.dev.bartendershandbook.module.dto.IngredientDTO;
import seniv.dev.bartendershandbook.module.entity.CocktailIngredient;
import seniv.dev.bartendershandbook.module.entity.Ingredient;
import seniv.dev.bartendershandbook.service.IngredientService;

import java.util.HashSet;

@Mapper(componentModel = "spring")
public abstract class IngredientMapper {

    @Autowired
    protected IngredientService ingredientService;

    public abstract IngredientDTO ingredientToIngredientDTO(Ingredient ingredient);

    @Mapping(target = "name", source = "cocktail.name")
    public abstract CocktailIngredientDTO cocktailIngredientToCocktailIngredientDTO(CocktailIngredient ci);

    @Mapping(target = "cocktails", ignore = true)
    public abstract Ingredient ingredientDTOtoIngredient(IngredientDTO dto);

    @AfterMapping
    protected void setIngredientRelations(@MappingTarget Ingredient ingredient, IngredientDTO dto) {

        if (dto.cocktails() == null) {
            ingredient.setCocktails(new HashSet<>());
            return;
        }

        ingredientService.setIngredientRelations(ingredient, dto);
    }
}
