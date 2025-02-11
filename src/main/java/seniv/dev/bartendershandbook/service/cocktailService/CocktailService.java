package seniv.dev.bartendershandbook.service.cocktailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seniv.dev.bartendershandbook.module.cocktail.Cocktail;
import seniv.dev.bartendershandbook.module.cocktailDTO.CocktailRequestDTO;
import seniv.dev.bartendershandbook.module.cocktailDTO.CocktailResponseDTO;
import seniv.dev.bartendershandbook.module.cocktails_ingredient.CocktailIngredient;
import seniv.dev.bartendershandbook.module.cocktails_ingredientDTO.CocktailIngredientDTO;
import seniv.dev.bartendershandbook.module.glass.Glass;
import seniv.dev.bartendershandbook.module.ingredient.Ingredient;
import seniv.dev.bartendershandbook.repository.CocktailRepository;
import seniv.dev.bartendershandbook.repository.GlassRepository;
import seniv.dev.bartendershandbook.repository.IngredientRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class CocktailService implements CocktailServiceImpl {

    private final CocktailRepository cocktailRepository;
    private final GlassRepository glassRepository;
    private final IngredientRepository ingredientRepository;

    @Autowired
    public CocktailService(
            CocktailRepository cocktailRepository,
            GlassRepository glassRepository,
            IngredientRepository ingredientRepository
    ) {
        this.cocktailRepository = cocktailRepository;
        this.glassRepository = glassRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public List<CocktailResponseDTO> getAllCocktails() {
        return cocktailRepository.findAll().stream().map(
                this::createCocktailResponseDTO
        ).toList();
    }

    public List<CocktailResponseDTO> getAllCocktailsThatContainsInfix(String infix) {
        return cocktailRepository.findByNameContaining(infix).stream().map(
                this::createCocktailResponseDTO
        ).toList();
    }

    public List<CocktailResponseDTO> getAllCocktailsWithAbvBetween(BigDecimal min, BigDecimal max) {
        return cocktailRepository.findByAbvBetween(min, max).stream().map(
                this::createCocktailResponseDTO
        ).toList();
    }

    public CocktailResponseDTO getCocktailById(Long id) {
        Cocktail cocktail = cocktailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cocktail not found by id=%s".formatted(id)));

        return createCocktailResponseDTO(cocktail);
    }

    public CocktailResponseDTO getCocktailByName(String name) {
        Cocktail cocktail = cocktailRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Cocktail not found by name=%s".formatted(name)));

        return createCocktailResponseDTO(cocktail);
    }

    public CocktailResponseDTO createCocktail(CocktailRequestDTO dto) {

        cocktailRepository.findByName(dto.getName()).ifPresent(temp -> {
            throw new IllegalArgumentException("name=%s is already taken".formatted(dto.getName()));
        });

        Glass glass = glassRepository.findByName(dto.getGlass().getName())
                .orElseThrow(() -> new IllegalArgumentException("Glass not found by name=%s".formatted(dto.getGlass().getName())));

        Cocktail cocktail = new Cocktail();

        cocktail.setName(dto.getName());
        cocktail.setVolume(dto.getVolume());
        cocktail.setAbv(dto.getAbv());
        cocktail.setGlass(glass);
        cocktail.setDescription(dto.getDescription());
        cocktail.setRecipe(dto.getRecipe());

        setCocktailRelations(cocktail, dto);

        return createCocktailResponseDTO(cocktailRepository.save(cocktail));
    }

    public void deleteCocktailById(Long id) {
        Cocktail cocktail = cocktailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cocktail not found by id=%s".formatted(id)));

        removeAllCocktailRelations(cocktail);

        cocktailRepository.delete(cocktail);
    }

    public void deleteCocktailByName(String name) {
        Cocktail cocktail = cocktailRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Cocktail not found by name=%s".formatted(name)));

        removeAllCocktailRelations(cocktail);

        cocktailRepository.delete(cocktail);
    }

    @Transactional
    public CocktailResponseDTO updateCocktail(Long id, CocktailRequestDTO dto) {
        Cocktail cocktail = cocktailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cocktail not found by id=%s".formatted(id)));

        String name = dto.getName();
        if (name != null && !name.equals(cocktail.getName())) {
            cocktailRepository.findByName(name).ifPresent(temp -> {
                throw new IllegalArgumentException("name=%s is already taken".formatted(name));
            });
            cocktail.setName(name);
        }

        Integer volume = dto.getVolume();
        if (volume != null && !volume.equals(cocktail.getVolume())) {
            cocktail.setVolume(volume);
        }

        Double abv = dto.getAbv();
        if (abv != null && !abv.equals(cocktail.getAbv())) {
            cocktail.setAbv(abv);
        }

        String glassName = dto.getGlass().getName();
        if (glassName != null && !glassName.equals(cocktail.getGlass().getName())) {
            cocktail.setGlass(glassRepository.findByName(glassName)
                    .orElseThrow(() -> new IllegalArgumentException("Glass not found by name=%s".formatted(glassName))));
        }

        String description = dto.getDescription();
        if (description != null && !description.equals(cocktail.getDescription())) {
            cocktail.setDescription(description);
        }

        String recipe = dto.getRecipe();
        if (recipe != null && !recipe.equals(cocktail.getRecipe())) {
            cocktail.setRecipe(recipe);
        }

        if (dto.getIngredients() != null) {
            removeAllCocktailRelations(cocktail);

            setCocktailRelations(cocktail, dto);
        }

        return createCocktailResponseDTO(cocktailRepository.save(cocktail));
    }

    private CocktailResponseDTO createCocktailResponseDTO(Cocktail cocktail) {
        return new CocktailResponseDTO(
                cocktail.getId(),
                cocktail.getName(),
                cocktail.getVolume(),
                cocktail.getAbv(),
                cocktail.getGlass().getName(),
                cocktail.getDescription(),
                cocktail.getRecipe(),
                cocktail.getIngredients().stream()
                        .map(ci -> new CocktailIngredientDTO(
                                ci.getIngredient().getName(),
                                ci.getAmount()
                        ))
                        .collect(toList())
        );
    }

    private void setCocktailRelations(Cocktail cocktail, CocktailRequestDTO dto) {

        List<String> ingredientsNames = dto.getIngredients().stream()
                .map(CocktailIngredientDTO::getName)
                .toList();

        Map<String, Ingredient> ingredientsMap = ingredientRepository.findByNameIn(ingredientsNames).stream()
                .collect(Collectors.toMap(Ingredient::getName, ingredient -> ingredient));

        List<String> notFoundNames = ingredientsNames.stream()
                .filter(name -> !ingredientsMap.containsKey(name))
                .toList();

        if (!notFoundNames.isEmpty()) {
            throw new IllegalArgumentException("Ingredients not found: " + notFoundNames);
        }

        List<CocktailIngredient> newIngredients = dto.getIngredients().stream()
                .map(cid ->
                        new CocktailIngredient(
                                cocktail,
                                ingredientsMap.get(cid.getName()),
                                cid.getAmount()
                        )
                ).toList();

        cocktail.getIngredients().addAll(newIngredients);
    }

    private void removeAllCocktailRelations(Cocktail cocktail) {
        for (CocktailIngredient ci : cocktail.getIngredients()) {
            ci.setCocktail(null);
            ci.getIngredient().getCocktails().remove(ci);
            ci.setIngredient(null);
        }
        cocktail.getIngredients().clear();
    }
}
