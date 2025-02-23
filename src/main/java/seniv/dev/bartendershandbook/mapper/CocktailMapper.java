package seniv.dev.bartendershandbook.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import seniv.dev.bartendershandbook.module.dto.CocktailDTO;
import seniv.dev.bartendershandbook.module.dto.CocktailIngredientDTO;
import seniv.dev.bartendershandbook.module.entity.Cocktail;
import seniv.dev.bartendershandbook.module.entity.CocktailIngredient;
import seniv.dev.bartendershandbook.module.entity.Glass;
import seniv.dev.bartendershandbook.service.CocktailService;
import seniv.dev.bartendershandbook.service.GlassService;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class CocktailMapper {

    @Autowired
    protected CocktailService cocktailService;

    @Autowired
    protected GlassService glassService;

    @Mapping(target = "glasses", qualifiedByName = "getGlassesNames")
    public abstract CocktailDTO cocktailToCocktailDTO(Cocktail cocktail);

    @Named("getGlassesNames")
    protected Set<String> getGlassesNames(Set<Glass> glasses) {
        return glasses
                .stream()
                .map(Glass::getName)
                .collect(Collectors.toSet());
    }

    @Mapping(target = "name", source = "ingredient.name")
    public abstract CocktailIngredientDTO cocktailIngredientToCocktailIngredientDTO(CocktailIngredient ci);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "glasses", qualifiedByName = "findGlassesByName")
    @Mapping(target = "ingredients", ignore = true)
    public abstract Cocktail cocktailDTOtoCocktail(CocktailDTO dto);

    @Named("findGlassesByName")
    protected Set<Glass> findGlassesByName(Set<String> glasses) {
        return glasses
                .stream()
                .map(glassService::getGlassByName)
                .collect(Collectors.toSet());
    }

    @AfterMapping
    protected void setCocktailRelations(@MappingTarget Cocktail cocktail, CocktailDTO dto) {

        if (dto.ingredients() == null) {
            cocktail.setIngredients(new HashSet<>());
            return;
        }

        cocktailService.setCocktailRelations(cocktail, dto);
    }
}
