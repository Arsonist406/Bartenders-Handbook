package seniv.dev.bartendershandbook.cocktails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seniv.dev.bartendershandbook.cocktails_ingredients.CocktailIngredient;
import seniv.dev.bartendershandbook.glasses.Glass;
import seniv.dev.bartendershandbook.glasses.GlassRepository;
import seniv.dev.bartendershandbook.ingredients.Ingredient;
import seniv.dev.bartendershandbook.ingredients.IngredientRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CocktailService {

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
                cocktail -> new CocktailResponseDTO(
                        cocktail.getId(),
                        cocktail.getName(),
                        cocktail.getVolumeInML(),
                        cocktail.getAbv(),
                        cocktail.getGlass().getName(),
                        cocktail.getDescription(),
                        cocktail.getRecipe(),
                        cocktail.getIngredients().stream()
                                .collect(Collectors.toMap(
                                        ci -> ci.getIngredient().getName(),
                                        ci -> ci.getAmount()
                                ))
                )).toList();
    }

    public List<CocktailResponseDTO> getAllCocktailsThatContainsInfix(String infix) {
        return cocktailRepository.findByNameContaining(infix).stream().map(
                cocktail -> new CocktailResponseDTO(
                        cocktail.getId(),
                        cocktail.getName(),
                        cocktail.getVolumeInML(),
                        cocktail.getAbv(),
                        cocktail.getGlass().getName(),
                        cocktail.getDescription(),
                        cocktail.getRecipe(),
                        cocktail.getIngredients().stream()
                                .collect(Collectors.toMap(
                                        ci -> ci.getIngredient().getName(),
                                        ci -> ci.getAmount()
                                ))
                )).toList();
    }

    public List<CocktailResponseDTO> getAllCocktailsWithAbvBetween(BigDecimal min, BigDecimal max) {
        if (Double.parseDouble(String.valueOf(min)) < 0.00) {
            throw new IllegalArgumentException("Min must be bigger than 0.00");
        }
        if (Double.parseDouble(String.valueOf(max)) > 99.99) {
            throw new IllegalArgumentException("Max must be smaller than 99.99");
        }

        return cocktailRepository.findByAbvBetween(min, max).stream().map(
                cocktail -> new CocktailResponseDTO(
                        cocktail.getId(),
                        cocktail.getName(),
                        cocktail.getVolumeInML(),
                        cocktail.getAbv(),
                        cocktail.getGlass().getName(),
                        cocktail.getDescription(),
                        cocktail.getRecipe(),
                        cocktail.getIngredients().stream()
                                .collect(Collectors.toMap(
                                        ci -> ci.getIngredient().getName(),
                                        ci -> ci.getAmount()
                                ))
                )).toList();
    }

    public CocktailResponseDTO getCocktailById(Long id) {
        Cocktail cocktail = cocktailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cocktail not found by id=%s".formatted(id)));

        return new CocktailResponseDTO(
                cocktail.getId(),
                cocktail.getName(),
                cocktail.getVolumeInML(),
                cocktail.getAbv(),
                cocktail.getGlass().getName(),
                cocktail.getDescription(),
                cocktail.getRecipe(),
                cocktail.getIngredients().stream()
                        .collect(Collectors.toMap(
                                ci -> ci.getIngredient().getName(),
                                ci -> ci.getAmount()
                        ))
        );
    }

    public CocktailResponseDTO getCocktailByName(String name) {
        Cocktail cocktail = cocktailRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Cocktail not found by name=%s".formatted(name)));

        return new CocktailResponseDTO(
                cocktail.getId(),
                cocktail.getName(),
                cocktail.getVolumeInML(),
                cocktail.getAbv(),
                cocktail.getGlass().getName(),
                cocktail.getDescription(),
                cocktail.getRecipe(),
                cocktail.getIngredients().stream()
                        .collect(Collectors.toMap(
                                ci -> ci.getIngredient().getName(),
                                ci -> ci.getAmount()
                        ))
        );
    }

    public CocktailResponseDTO createCocktail(CocktailRequestDTO dto) {

        String name = dto.getName();
        if (name == null) {
            throw new IllegalArgumentException("Name can't be null");
        }
        if (cocktailRepository.findByName(name).isPresent()) {
            throw new IllegalArgumentException("Name already taken");
        }
        if (name.length() > 50) {
            throw new IllegalArgumentException("Name length must be smaller than 50 symbols");
        }

        Integer volume = dto.getVolumeInML();
        if (volume == null) {
            throw new IllegalArgumentException("Volume can't be null");
        }
        if (volume <= 0) {
            throw new IllegalArgumentException("Volume must be bigger than 0 ml");
        }

        Double abv = dto.getAbv();
        if (abv == null) {
            throw new IllegalArgumentException("ABV can't be null");
        }
        if (abv > 99.99 || abv < 0.00) {
            throw new IllegalArgumentException("ABV must be between 0% and 99.99%");
        }

        String glassName = dto.getGlass();
        Glass glass;
        if (glassName == null) {
            glass = glassRepository.findByName("Default Glass")
                    .orElseThrow(() -> new IllegalArgumentException("Default Glass not found"));
        } else {
            glass = glassRepository.findByName(glassName)
                            .orElseThrow(() -> new IllegalArgumentException("Glass not found by name=%s".formatted(glassName)));
        }

        String description = dto.getDescription();
        if (description.length() > 2000) {
            throw new IllegalArgumentException("Description length must be smaller than 2000 symbols");
        }

        String recipe = dto.getRecipe();
        if (recipe.length() > 2000) {
            throw new IllegalArgumentException("Recipe length must be smaller than 2000 symbols");
        }

        Cocktail cocktail = new Cocktail();
        cocktail.setName(name);
        cocktail.setVolumeInML(volume);
        cocktail.setAbv(abv);
        cocktail.setGlass(glass);
        cocktail.setDescription(description);
        cocktail.setRecipe(recipe);

        List<CocktailIngredient> ingredients = dto.getIngredients().entrySet().stream()
                .map(entry -> {
                    Ingredient ingredient = ingredientRepository.findByName(entry.getKey())
                            .orElseThrow(() -> new IllegalArgumentException("Ingredient not found by name=%s".formatted(entry.getKey())));

                    CocktailIngredient ci = new CocktailIngredient();
                    ci.setCocktail(cocktail);
                    ci.setIngredient(ingredient);
                    ci.setAmount(entry.getValue());

                    return ci;
                })
                .toList();

        cocktail.getIngredients().addAll(ingredients);

        Cocktail savedCocktail = cocktailRepository.save(cocktail);

        return new CocktailResponseDTO(
                savedCocktail.getId(),
                savedCocktail.getName(),
                savedCocktail.getVolumeInML(),
                savedCocktail.getAbv(),
                savedCocktail.getGlass().getName(),
                savedCocktail.getDescription(),
                savedCocktail.getRecipe(),
                savedCocktail.getIngredients().stream()
                        .collect(Collectors.toMap(
                                ci -> ci.getIngredient().getName(),
                                ci -> ci.getAmount()
                        ))
        );
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
            if (cocktailRepository.findByName(name).isPresent()) {
                throw new IllegalArgumentException("Name already taken");
            }
            if (name.length() > 50) {
                throw new IllegalArgumentException("Name length must be smaller than 50 symbols");
            }
            cocktail.setName(name);
        }

        Integer volume = dto.getVolumeInML();
        if (volume != null && !volume.equals(cocktail.getVolumeInML())) {
            if (volume <= 0) {
                throw new IllegalArgumentException("Volume must be bigger than 0 ml");
            }
            cocktail.setVolumeInML(volume);
        }

        Double abv = dto.getAbv();
        if (abv != null && !abv.equals(cocktail.getAbv())) {
            if (abv > 99.99 || abv < 0.00) {
                throw new IllegalArgumentException("ABV must be between 0% and 99.99%");
            }
            cocktail.setAbv(abv);
        }

        String glass = dto.getGlass();
        if (glass != null && !glass.equals(cocktail.getGlass().getName())) {
            cocktail.setGlass(glassRepository.findByName(glass)
                    .orElseThrow(() -> new IllegalArgumentException("Glass not found by name=%s".formatted(glass))));
        }

        String description = dto.getDescription();
        if (description != null && !description.equals(cocktail.getDescription())) {
            if (description.length() > 2000) {
                throw new IllegalArgumentException("Description length must be smaller than 2000 symbols");
            }
            cocktail.setDescription(description);
        }

        String recipe = dto.getRecipe();
        if (recipe != null && !recipe.equals(cocktail.getRecipe())) {
            if (recipe.length() > 2000) {
                throw new IllegalArgumentException("Recipe length must be smaller than 2000 symbols");
            }
            cocktail.setDescription(description);
        }

        if (dto.getIngredients() != null) {
            removeAllCocktailRelations(cocktail);

            List<CocktailIngredient> newIngredients = dto.getIngredients().entrySet().stream()
                    .map(entry -> {
                        String ingredientName = entry.getKey();
                        String amount = entry.getValue();

                        Ingredient ingredient = ingredientRepository.findByName(ingredientName)
                                .orElseThrow(() -> new IllegalArgumentException("Ingredient not by name=%s".formatted(ingredientName)));

                        CocktailIngredient ci = new CocktailIngredient();
                        ci.setCocktail(cocktail);
                        ci.setIngredient(ingredient);
                        ci.setAmount(amount);

                        return ci;
                    })
                    .toList();

            cocktail.getIngredients().addAll(newIngredients);
        }

        Cocktail savedCocktail = cocktailRepository.save(cocktail);

        return new CocktailResponseDTO(
                savedCocktail.getId(),
                savedCocktail.getName(),
                savedCocktail.getVolumeInML(),
                savedCocktail.getAbv(),
                savedCocktail.getGlass().getName(),
                savedCocktail.getDescription(),
                savedCocktail.getRecipe(),
                savedCocktail.getIngredients().stream()
                        .collect(Collectors.toMap(
                                ci -> ci.getIngredient().getName(),
                                ci -> ci.getAmount()
                        ))
        );
    }

    private void removeAllCocktailRelations(Cocktail cocktail) {
        for (CocktailIngredient ci : cocktail.getIngredients()) {
            ci.setCocktail(null);
            if (ci.getIngredient() != null) {
                ci.getIngredient().getCocktails().remove(ci);
            }
            ci.setIngredient(null);
        }
        cocktail.getIngredients().clear();
    }
}
