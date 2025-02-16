package seniv.dev.bartendershandbook.service.cocktailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seniv.dev.bartendershandbook.module.DTO.cocktailDTO.CocktailRequestDTO;
import seniv.dev.bartendershandbook.module.DTO.cocktailDTO.CocktailResponseDTO;
import seniv.dev.bartendershandbook.module.DTO.cocktails_ingredientDTO.CocktailIngredientDTO;
import seniv.dev.bartendershandbook.module.entity.cocktail.Cocktail;
import seniv.dev.bartendershandbook.module.entity.cocktailIngredient.CocktailIngredient;
import seniv.dev.bartendershandbook.module.entity.glass.Glass;
import seniv.dev.bartendershandbook.module.entity.ingredient.Ingredient;
import seniv.dev.bartendershandbook.repository.CocktailRepository;
import seniv.dev.bartendershandbook.service.glassService.GlassServiceImpl;
import seniv.dev.bartendershandbook.service.ingredientService.IngredientServiceImpl;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class CocktailService implements CocktailServiceImpl {

    private final CocktailRepository cocktailRepository;

    private final GlassServiceImpl glassService;
    private final IngredientServiceImpl ingredientService;

    @Autowired
    public CocktailService(
            CocktailRepository cocktailRepository,
            @Lazy GlassServiceImpl glassService,
            @Lazy IngredientServiceImpl ingredientService
    ) {
        this.cocktailRepository = cocktailRepository;
        this.glassService = glassService;
        this.ingredientService = ingredientService;
    }

    public List<CocktailResponseDTO> getAllCocktails() {
        return cocktailRepository.findAll().stream().map(
                this::createCocktailResponseDTO
        ).toList();
    }

    public List<CocktailResponseDTO> searchCocktails(
            String infix,
            BigDecimal min,
            BigDecimal max
    ) {
        if (infix == null) {
            infix = "";
        }
        if (min == null) {
            min = BigDecimal.valueOf(0.00);
        }
        if (max == null) {
            max = BigDecimal.valueOf(99.99);
        }
        if (Double.parseDouble(String.valueOf(min)) > (Double.parseDouble(String.valueOf(max)))) {
            throw new IllegalArgumentException("min must be smaller than max");
        }

        return cocktailRepository.findByNameContainingAndAbvBetween(infix, min, max).stream().map(
                this::createCocktailResponseDTO
        ).toList();
    }

    public CocktailResponseDTO getCocktailById(Long id) {
        Cocktail cocktail = cocktailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cocktail not found by id=%s".formatted(id)));

        return createCocktailResponseDTO(cocktail);
    }

    public CocktailResponseDTO createCocktail(CocktailRequestDTO dto) {

        cocktailRepository.findByName(dto.getName()).ifPresent(temp -> {
            throw new IllegalArgumentException("name=%s is already taken".formatted(dto.getName()));
        });

        Set<Glass> glasses = dto.getGlasses().stream().map(glassService::getGlassByName).collect(Collectors.toSet());

        Cocktail cocktail = new Cocktail(
                dto.getName(),
                dto.getVolume(),
                dto.getAbv(),
                glasses,
                dto.getDescription(),
                dto.getRecipe(),
                new ArrayList<>()
        );

        setCocktailRelations(cocktail, dto);

        return createCocktailResponseDTO(cocktailRepository.save(cocktail));
    }

    public void deleteCocktailById(Long id) {
        Cocktail cocktail = cocktailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cocktail not found by id=%s".formatted(id)));

        removeAllCocktailRelations(cocktail);

        cocktailRepository.delete(cocktail);
    }

    @Transactional
    public CocktailResponseDTO updateCocktailById(Long id, CocktailRequestDTO dto) {
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

        Set<String> glassesName = dto.getGlasses();
        if (glassesName != null) {
            cocktail.getGlasses().clear();

            cocktail.getGlasses().addAll(glassesName.stream()
                    .map(glassService::getGlassByName).collect(Collectors.toSet()));
        }

        String description = dto.getDescription();
        if (description != null && !description.equals(cocktail.getDescription())) {
            cocktail.setDescription(description);
        }

        String recipe = dto.getRecipe();
        if (recipe != null && !recipe.equals(cocktail.getRecipe())) {
            cocktail.setRecipe(recipe);
        }

        if (!dto.getIngredients().isEmpty()) {
            removeAllCocktailRelations(cocktail);

            setCocktailRelations(cocktail, dto);
        }

        return createCocktailResponseDTO(cocktailRepository.save(cocktail));
    }


    public List<Cocktail> getCocktailsByGlass(Glass glass) {
        Set<Glass> glasses = new HashSet<>();
        glasses.add(glass);
        return cocktailRepository.findByGlassesContaining(glasses);
    }

    public List<Cocktail> getCocktailsIn(List<String> cocktailsName) {
        return cocktailRepository.findByNameIn(cocktailsName);
    }


    private CocktailResponseDTO createCocktailResponseDTO(Cocktail cocktail) {
        return new CocktailResponseDTO(
                cocktail.getId(),
                cocktail.getName(),
                cocktail.getVolume(),
                cocktail.getAbv(),
                cocktail.getGlasses().stream().map(
                        Glass::getName
                ).collect(Collectors.toSet()),
                cocktail.getDescription(),
                cocktail.getRecipe(),
                cocktail.getIngredients().stream()
                        .map(ci -> new CocktailIngredientDTO(
                                ci.getIngredient().getName(),
                                ci.getAmount()
                        )).toList()
        );
    }

    private void setCocktailRelations(Cocktail cocktail, CocktailRequestDTO dto) {

        List<String> ingredientsNames = dto.getIngredients().stream()
                .map(CocktailIngredientDTO::getName)
                .toList();

        Map<String, Ingredient> ingredientsMap = ingredientService.getIngredientsIn(ingredientsNames).stream()
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
