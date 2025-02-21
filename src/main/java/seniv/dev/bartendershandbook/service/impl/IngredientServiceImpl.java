package seniv.dev.bartendershandbook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seniv.dev.bartendershandbook.exceptionHandler.exception.IsAlreadyTakenException;
import seniv.dev.bartendershandbook.exceptionHandler.exception.MinMaxException;
import seniv.dev.bartendershandbook.exceptionHandler.exception.NotFoundByException;
import seniv.dev.bartendershandbook.exceptionHandler.exception.NotFoundInException;
import seniv.dev.bartendershandbook.mapper.IngredientMapper;
import seniv.dev.bartendershandbook.module.dto.CocktailIngredientDTO;
import seniv.dev.bartendershandbook.module.dto.IngredientDTO;
import seniv.dev.bartendershandbook.module.entity.Cocktail;
import seniv.dev.bartendershandbook.module.entity.CocktailIngredient;
import seniv.dev.bartendershandbook.module.entity.Ingredient;
import seniv.dev.bartendershandbook.module.entity.IngredientCategory;
import seniv.dev.bartendershandbook.repository.IngredientRepository;
import seniv.dev.bartendershandbook.service.CocktailService;
import seniv.dev.bartendershandbook.service.IngredientService;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final CocktailService cocktailService;
    private final IngredientMapper ingredientMapper;

    @Autowired
    public IngredientServiceImpl(
            IngredientRepository ingredientRepository,
            @Lazy CocktailService cocktailService,
            @Lazy IngredientMapper ingredientMapper
    ) {
        this.ingredientRepository = ingredientRepository;
        this.cocktailService = cocktailService;
        this.ingredientMapper = ingredientMapper;
    }

    public Set<IngredientDTO> searchIngredients(
            String infix,
            BigDecimal min,
            BigDecimal max
    ) {
        if (Double.parseDouble(String.valueOf(min)) > (Double.parseDouble(String.valueOf(max)))) {
            throw new MinMaxException("min must be smaller than max");
        }

       return ingredientRepository.findByNameContainingAndAbvBetween(infix, min, max)
               .stream()
               .map(ingredientMapper::ingredientToIngredientDTO)
               .collect(Collectors.toSet());
    }

    public IngredientDTO getIngredientById(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new NotFoundByException(Ingredient.class, "id", id));

        return ingredientMapper.ingredientToIngredientDTO(ingredient);
    }

    public IngredientDTO createIngredient(IngredientDTO dto) {

        ingredientRepository.findByName(dto.name()).ifPresent(_ -> {
            throw new IsAlreadyTakenException("name", dto.name());
        });

        //todo: краще розділити?
        //todo: чи є сенс dto->ingredient->dto? повертати dto, що прийшло - не варік: нема id; повертати лише 200 ОК??
        return ingredientMapper.ingredientToIngredientDTO(ingredientRepository.save(ingredientMapper.ingredientDTOtoIngredient(dto)));
    }

    public void deleteIngredientById(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new NotFoundByException(Ingredient.class, "id", id));

        removeIngredientRelations(ingredient);

        ingredientRepository.delete(ingredient);
    }

    @Transactional
    public IngredientDTO updateIngredientById(Long id, IngredientDTO dto) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new NotFoundByException(Ingredient.class, "id", id));

        String newName = dto.name();
        String oldName = ingredient.getName();
        if (newName != null && !newName.equals(oldName)) {
            ingredientRepository.findByName(newName).ifPresent(_ -> {
                throw new IsAlreadyTakenException("name", dto.name());
            });
            ingredient.setName(newName);
        }

        BigDecimal newAbv = dto.abv();
        BigDecimal oldAbv = ingredient.getAbv();
        if (newAbv != null && !newAbv.equals(oldAbv)) {
            ingredient.setAbv(newAbv);
        }

        IngredientCategory newCategory = dto.category();
        IngredientCategory oldCategory = ingredient.getCategory();
        if (newCategory != null && !newCategory.equals(oldCategory)) {
            ingredient.setCategory(newCategory);
        }

        String newDescription = dto.description();
        String oldDescription = ingredient.getDescription();
        if (newDescription != null && !newDescription.equals(oldDescription)) {
            ingredient.setDescription(newDescription);
        }

        Set<CocktailIngredientDTO> newCocktails = dto.cocktails();
        Set<CocktailIngredientDTO> oldCocktails = ingredient.getCocktails()
                .stream()
                .map(ingredientMapper::cocktailIngredientToCocktailIngredientDTO)
                .collect(Collectors.toSet());
        if (newCocktails != null && !newCocktails.equals(oldCocktails)) {
            removeIngredientRelations(ingredient);

            setIngredientRelations(ingredient, dto);
        }

        return ingredientMapper.ingredientToIngredientDTO(ingredientRepository.save(ingredient));
    }


    public Set<Ingredient> getIngredientsIn(Set<String> ingredientsNames) {
        return ingredientRepository.findByNameIn(ingredientsNames);
    }

    public void setIngredientRelations(Ingredient ingredient, IngredientDTO dto) {

        Set<String> cocktailsNames = dto.cocktails()
                .stream()
                .map(CocktailIngredientDTO::name)
                .collect(Collectors.toSet());

        Map<String, Cocktail> cocktailsMap = cocktailService.getCocktailsIn(cocktailsNames)
                .stream()
                .collect(Collectors.toMap(Cocktail::getName, cocktail -> cocktail));

        Set<String> notFoundNames = cocktailsNames
                .stream()
                .filter(name -> !cocktailsMap.containsKey(name))
                .collect(Collectors.toSet());

        if (!notFoundNames.isEmpty()) {
            throw new NotFoundInException(Cocktail.class, "cocktails.name", notFoundNames);
        }

        Set<CocktailIngredient> newCocktails = dto.cocktails()
                .stream()
                .map(cid ->
                        new CocktailIngredient(
                                cocktailsMap.get(cid.name()),
                                ingredient,
                                cid.amount()
                        )
                ).collect(Collectors.toSet());

        ingredient.setCocktails(newCocktails);
    }

    private void removeIngredientRelations(Ingredient ingredient) {
        for (CocktailIngredient ci : ingredient.getCocktails()) {
            ci.getCocktail().getIngredients().remove(ci);
        }
        ingredient.getCocktails().clear();
    }
}
