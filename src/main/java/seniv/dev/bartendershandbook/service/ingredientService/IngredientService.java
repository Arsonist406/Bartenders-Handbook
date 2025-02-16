package seniv.dev.bartendershandbook.service.ingredientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seniv.dev.bartendershandbook.module.DTO.cocktails_ingredientDTO.CocktailIngredientDTO;
import seniv.dev.bartendershandbook.module.DTO.ingredientDTO.IngredientRequestDTO;
import seniv.dev.bartendershandbook.module.DTO.ingredientDTO.IngredientResponseDTO;
import seniv.dev.bartendershandbook.module.entity.cocktail.Cocktail;
import seniv.dev.bartendershandbook.module.entity.cocktailIngredient.CocktailIngredient;
import seniv.dev.bartendershandbook.module.entity.ingredient.Category;
import seniv.dev.bartendershandbook.module.entity.ingredient.Ingredient;
import seniv.dev.bartendershandbook.repository.CocktailRepository;
import seniv.dev.bartendershandbook.repository.IngredientRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class IngredientService implements IngredientServiceImpl {

    private final IngredientRepository ingredientRepository;
    private final CocktailRepository cocktailRepository;    //TODO: репозиторій поміняти на сервіс

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository, CocktailRepository cocktailRepository) {
        this.ingredientRepository = ingredientRepository;
        this.cocktailRepository = cocktailRepository;
    }

    public List<IngredientResponseDTO> getAllIngredients() {
        return ingredientRepository.findAll().stream().map(
                this::createIngredientResponseDTO
        ).toList();
    }

    public List<IngredientResponseDTO> searchIngredients(
            String infix,
            BigDecimal min,
            BigDecimal max,
            Category category
    ) {
       return ingredientRepository.findByNameContainingAndAbvBetweenAndCategory(infix, min, max, category).stream().map(
               this::createIngredientResponseDTO
       ).toList();
    }

    public IngredientResponseDTO getIngredientById(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ingredient not found by id=%s".formatted(id)));

        return createIngredientResponseDTO(ingredient);
    }

    public IngredientResponseDTO createIngredient(IngredientRequestDTO dto) {

        ingredientRepository.findByName(dto.getName()).ifPresent(temp -> {
                    throw new IllegalArgumentException("name=%s is already taken".formatted(dto.getName()));
                });

        Ingredient ingredient = new Ingredient();

        ingredient.setName(dto.getName());
        ingredient.setAbv(dto.getAbv());
        ingredient.setCategory(dto.getCategory());
        ingredient.setDescription(dto.getDescription());

        if (dto.getCocktails() != null) {
            setIngredientRelations(ingredient, dto);
        }

        return createIngredientResponseDTO(ingredientRepository.save(ingredient));
    }

    public void deleteIngredientById(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ingredient not found by id=%s".formatted(id)));

        removeAllIngredientRelations(ingredient);

        ingredientRepository.delete(ingredient);
    }

    @Transactional
    public IngredientResponseDTO updateIngredientById(Long id, IngredientRequestDTO dto) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ingredient not found by id=%s".formatted(id)));

        String name = dto.getName();
        if (name != null && !name.equals(ingredient.getName())) {
            ingredientRepository.findByName(name).ifPresent(temp -> {
                throw new IllegalArgumentException("name=%s is already taken".formatted(name));
            });
            ingredient.setName(name);
        }

        Double abv = dto.getAbv();
        if (abv != null && !abv.equals(ingredient.getAbv())) {
            ingredient.setAbv(abv);
        }

        Category category = dto.getCategory();
        if (category != null && !category.equals(ingredient.getCategory())) {
            ingredient.setCategory(category);
        }

        String description = dto.getDescription();
        if (description != null && !description.equals(ingredient.getDescription())) {
            ingredient.setDescription(description);
        }

        if (dto.getCocktails() != null) {
            removeAllIngredientRelations(ingredient);

            setIngredientRelations(ingredient, dto);
        }

        return createIngredientResponseDTO(ingredientRepository.save(ingredient));
    }


    private IngredientResponseDTO createIngredientResponseDTO(Ingredient ingredient) {
        return new IngredientResponseDTO(
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getAbv(),
                ingredient.getCategory(),
                ingredient.getDescription(),
                ingredient.getCocktails().stream()
                        .map(ci -> new CocktailIngredientDTO(
                                ci.getCocktail().getName(),
                                ci.getAmount()
                        )).collect(toList())
        );
    }

    private void setIngredientRelations(Ingredient ingredient, IngredientRequestDTO dto) {

        List<String> cocktailsNames = dto.getCocktails().stream()
                .map(CocktailIngredientDTO::getName)
                .toList();

        Map<String, Cocktail> cocktailsMap = cocktailRepository.findByNameIn(cocktailsNames).stream()
                .collect(Collectors.toMap(Cocktail::getName, cocktail -> cocktail));

        List<String> notFoundNames = cocktailsNames.stream()
                .filter(name -> !cocktailsMap.containsKey(name))
                .toList();

        if (!notFoundNames.isEmpty()) {
            throw new IllegalArgumentException("Cocktails not found: " + notFoundNames);
        }

        List<CocktailIngredient> newCocktails = dto.getCocktails().stream()
                .map(cid ->
                        new CocktailIngredient(
                                cocktailsMap.get(cid.getName()),
                                ingredient,
                                cid.getAmount()
                        )
                ).toList();

        ingredient.getCocktails().addAll(newCocktails);
    }

    private void removeAllIngredientRelations(Ingredient ingredient) {
        for (CocktailIngredient ci : ingredient.getCocktails()) {
            ci.setIngredient(null);
            ci.getCocktail().getIngredients().remove(ci);
            ci.setCocktail(null);
        }
        ingredient.getCocktails().clear();
    }
}
