package seniv.dev.bartendershandbook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seniv.dev.bartendershandbook.exceptionHandler.exception.IsAlreadyTakenException;
import seniv.dev.bartendershandbook.exceptionHandler.exception.MinMaxException;
import seniv.dev.bartendershandbook.exceptionHandler.exception.NotFoundByException;
import seniv.dev.bartendershandbook.exceptionHandler.exception.NotFoundInException;
import seniv.dev.bartendershandbook.mapper.CocktailMapper;
import seniv.dev.bartendershandbook.module.dto.CocktailDTO;
import seniv.dev.bartendershandbook.module.dto.CocktailIngredientDTO;
import seniv.dev.bartendershandbook.module.entity.Cocktail;
import seniv.dev.bartendershandbook.module.entity.CocktailIngredient;
import seniv.dev.bartendershandbook.module.entity.Glass;
import seniv.dev.bartendershandbook.module.entity.Ingredient;
import seniv.dev.bartendershandbook.repository.CocktailRepository;
import seniv.dev.bartendershandbook.service.CocktailService;
import seniv.dev.bartendershandbook.service.GlassService;
import seniv.dev.bartendershandbook.service.IngredientService;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class CocktailServiceImpl implements CocktailService {

    private final CocktailRepository cocktailRepository;

    private final GlassService glassService;
    private final IngredientService ingredientService;
    private final CocktailMapper cocktailMapper;

    @Autowired
    public CocktailServiceImpl(
            CocktailRepository cocktailRepository,
            @Lazy GlassService glassService,
            @Lazy IngredientService ingredientService,
            @Lazy CocktailMapper cocktailMapper
    ) {
        this.cocktailRepository = cocktailRepository;
        this.glassService = glassService;
        this.ingredientService = ingredientService;
        this.cocktailMapper = cocktailMapper;
    }

    public Set<CocktailDTO> searchCocktails(
            String infix,
            BigDecimal min,
            BigDecimal max
    ) {
        if (Double.parseDouble(String.valueOf(min)) > (Double.parseDouble(String.valueOf(max)))) {
            throw new MinMaxException("min must be smaller than max");
        }

        return cocktailRepository.findByNameContainingAndAbvBetween(infix, min, max)
                .stream()
                .map(cocktailMapper::cocktailToCocktailDTO)
                .collect(Collectors.toSet());
    }

    public CocktailDTO getCocktailById(Long id) {
        return cocktailMapper.cocktailToCocktailDTO(cocktailRepository.findById(id)
                .orElseThrow(() -> new NotFoundByException(Cocktail.class, "id", id)));
    }

    public CocktailDTO createCocktail(CocktailDTO dto) {

        cocktailRepository.findByName(dto.name()).ifPresent(temp -> {
            throw new IsAlreadyTakenException("name", dto.name());
        });

        return cocktailMapper.cocktailToCocktailDTO(cocktailRepository.save(cocktailMapper.cocktailDTOtoCocktail(dto)));
    }

    public void deleteCocktailById(Long id) {
        Cocktail cocktail = cocktailRepository.findById(id)
                .orElseThrow(() -> new NotFoundByException(Cocktail.class, "id", id));

        removeAllCocktailRelations(cocktail);

        cocktailRepository.delete(cocktail);
    }

    @Transactional
    public CocktailDTO updateCocktailById(Long id, CocktailDTO dto) {
        Cocktail cocktail = cocktailRepository.findById(id)
                .orElseThrow(() -> new NotFoundByException(Cocktail.class, "id", id));

        String newName = dto.name();
        String oldName = cocktail.getName();
        if (newName != null && !newName.equals(oldName)) {
            cocktailRepository.findByName(newName).ifPresent(_ -> {
                throw new IsAlreadyTakenException("name", dto.name());
            });
            cocktail.setName(newName);
        }

        Integer newVolume = dto.volume();
        Integer oldVolume = cocktail.getVolume();
        if (newVolume != null && !newVolume.equals(oldVolume)) {
            cocktail.setVolume(newVolume);
        }

        BigDecimal newAbv = dto.abv();
        BigDecimal oldAbv = cocktail.getAbv();
        if (newAbv != null && !newAbv.equals(oldAbv)) {
            cocktail.setAbv(newAbv);
        }

        Set<String> newGlassesNames = dto.glasses();
        Set<String> oldGlassesNames = cocktail.getGlasses().stream().map(
                Glass::getName
        ).collect(Collectors.toSet());
        if (newGlassesNames != null && !newGlassesNames.equals(oldGlassesNames)) {
            cocktail.getGlasses().clear();

            cocktail.getGlasses().addAll(newGlassesNames
                    .stream()
                    .map(glassService::getGlassByName)
                    .collect(Collectors.toSet()));
        }

        String newDescription = dto.description();
        String oldDescription = cocktail.getDescription();
        if (newDescription != null && !newDescription.equals(oldDescription)) {
            cocktail.setDescription(newDescription);
        }

        String newRecipe = dto.recipe();
        String oldRecipe = cocktail.getRecipe();
        if (newRecipe != null && !newRecipe.equals(oldRecipe)) {
            cocktail.setRecipe(newRecipe);
        }

        Set<CocktailIngredientDTO> newCocktails = dto.ingredients();
        Set<CocktailIngredientDTO> oldCocktails = cocktail.getIngredients()
                .stream()
                .map(cocktailMapper::cocktailIngredientToCocktailIngredientDTO)
                .collect(Collectors.toSet());
        if (newCocktails != null && !newCocktails.equals(oldCocktails)) {
            removeAllCocktailRelations(cocktail);

            setCocktailRelations(cocktail, dto);
        }

        return cocktailMapper.cocktailToCocktailDTO(cocktailRepository.save(cocktail));
    }


    public Set<Cocktail> getCocktailsByGlass(Glass glass) {
        Set<Glass> glasses = new HashSet<>();
        glasses.add(glass);
        return cocktailRepository.findByGlassesContaining(glasses);
    }

    public Set<Cocktail> getCocktailsIn(Set<String> cocktailsName) {
        return cocktailRepository.findByNameIn(cocktailsName);
    }

    public void setCocktailRelations(Cocktail cocktail, CocktailDTO dto) {

        Set<String> ingredientsNames = dto.ingredients()
                .stream()
                .map(CocktailIngredientDTO::name)
                .collect(Collectors.toSet());

        Map<String, Ingredient> ingredientsMap = ingredientService.getIngredientsIn(ingredientsNames)
                .stream()
                .collect(Collectors.toMap(Ingredient::getName, ingredient -> ingredient));

        Set<String> notFoundNames = ingredientsNames
                .stream()
                .filter(name -> !ingredientsMap.containsKey(name))
                .collect(Collectors.toSet());

        if (!notFoundNames.isEmpty()) {
            throw new NotFoundInException(Ingredient.class, "ingredients.name", notFoundNames);
        }

        Set<CocktailIngredient> newIngredients = dto.ingredients()
                .stream()
                .map(cid ->
                        new CocktailIngredient(
                                cocktail,
                                ingredientsMap.get(cid.name()),
                                cid.amount()
                        )
                ).collect(Collectors.toSet());

        cocktail.setIngredients(newIngredients);
    }

    private void removeAllCocktailRelations(Cocktail cocktail) {
        for (CocktailIngredient ci : cocktail.getIngredients()) {
            ci.getIngredient().getCocktails().remove(ci);
        }
        cocktail.getIngredients().clear();
    }
}
