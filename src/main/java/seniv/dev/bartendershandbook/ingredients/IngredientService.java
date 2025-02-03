package seniv.dev.bartendershandbook.ingredients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import seniv.dev.bartendershandbook.cocktails.Cocktail;
import seniv.dev.bartendershandbook.cocktails.CocktailRepository;
import seniv.dev.bartendershandbook.cocktails.CocktailResponseDTO;
import seniv.dev.bartendershandbook.cocktails_ingredients.CocktailIngredient;
import seniv.dev.bartendershandbook.glasses.Glass;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final CocktailRepository cocktailRepository;

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

    public List<IngredientResponseDTO> getAllIngredientsThatContainsInfix(String infix) {
        return ingredientRepository.findByNameContaining(infix).stream().map(
                this::createIngredientResponseDTO
        ).toList();
    }

    public List<IngredientResponseDTO> getAllIngredientsByCategory(Category category) {
        return ingredientRepository.findByCategory(category).stream().map(
                this::createIngredientResponseDTO
        ).toList();
    }

    public IngredientResponseDTO getIngredientById(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ingredient not found by id=%s".formatted(id)));

        return createIngredientResponseDTO(ingredient);
    }

    public IngredientResponseDTO getIngredientByName(String name) {
        Ingredient ingredient = ingredientRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Ingredient not found by name=%s".formatted(name)));

        return createIngredientResponseDTO(ingredient);
    }

    public IngredientResponseDTO createIngredient(IngredientRequestDTO dto) {

        String name = dto.getName();
        if (name == null) {
            throw new IllegalArgumentException("Name can't be null");
        }
        if (ingredientRepository.findByName(name).isPresent()) {
            throw new IllegalArgumentException("Name already taken");
        }
        if (name.length() > 50) {
            throw new IllegalArgumentException("Name length must be smaller than 50 symbols");
        }

        Double abv = dto.getAbv();
        if (abv == null) {
            throw new IllegalArgumentException("ABV can't be null");
        }
        if (abv > 99.99 || abv < 0.00) {
            throw new IllegalArgumentException("ABV must be between 0% and 99.99%");
        }

        Category category = dto.getCategory();
        if (category == null) {
            throw new IllegalArgumentException("Category can't be null");
        }

        String description = dto.getDescription();
        if (description.length() > 2000) {
            throw new IllegalArgumentException("Description length must be smaller than 2000 symbols");
        }

        Ingredient ingredient = new Ingredient();

        ingredient.setName(name);
        ingredient.setAbv(abv);
        ingredient.setCategory(category);
        ingredient.setDescription(description);

        setIngredientRelations(ingredient, dto);

        Ingredient savedIngredient = ingredientRepository.save(ingredient);

        return createIngredientResponseDTO(savedIngredient);
    }

    public void deleteIngredientById(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ingredient not found by id=%s".formatted(id)));

        removeAllIngredientRelations(ingredient);

        ingredientRepository.delete(ingredient);
    }

    public void deleteIngredientByName(String name) {
        Ingredient ingredient = ingredientRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Ingredient not found by name=%s".formatted(name)));

        removeAllIngredientRelations(ingredient);

        ingredientRepository.delete(ingredient);
    }

    @Transactional
    public IngredientResponseDTO updateIngredient(Long id, IngredientRequestDTO dto) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ingredient not found by id=%s".formatted(id)));

        String name = dto.getName();
        if (name != null && !name.equals(ingredient.getName())) {
            if (ingredientRepository.findByName(name).isPresent()) {
                throw new IllegalArgumentException("Name already taken");
            }
            if (name.length() > 50) {
                throw new IllegalArgumentException("Name length must be smaller than 50 symbols");
            }
            ingredient.setName(name);
        }

        Double abv = dto.getAbv();
        if (abv != null && !abv.equals(ingredient.getAbv())) {
            if (abv > 99.99 || abv < 0.00) {
                throw new IllegalArgumentException("ABV must be between 0% and 99.99%");
            }
            ingredient.setAbv(abv);
        }

        Category category = dto.getCategory();
        if (category != null && !category.equals(ingredient.getCategory())) {
            ingredient.setCategory(category);
        }

        String description = dto.getDescription();
        if (description != null && !description.equals(ingredient.getDescription())) {
            if (description.length() > 2000) {
                throw new IllegalArgumentException("Description length must be smaller than 2000 symbols");
            }
            ingredient.setDescription(description);
        }

        if (dto.getCocktails() != null) {
            removeAllIngredientRelations(ingredient);

            setIngredientRelations(ingredient, dto);
        }

        Ingredient savedIngredient = ingredientRepository.save(ingredient);

        return createIngredientResponseDTO(savedIngredient);
    }

    private IngredientResponseDTO createIngredientResponseDTO(Ingredient ingredient) {
        return new IngredientResponseDTO(
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getAbv(),
                ingredient.getCategory(),
                ingredient.getDescription(),
                ingredient.getCocktails().stream()
                        .collect(Collectors.toMap(
                                ci -> ci.getIngredient().getName(),
                                ci -> ci.getAmount()
                        ))
        );
    }

    private void setIngredientRelations(Ingredient ingredient, IngredientRequestDTO dto) {
        List<CocktailIngredient> newCocktails = dto.getCocktails().entrySet().stream()
                .map(entry -> {
                    String cocktailName = entry.getKey();
                    String amount = entry.getValue();

                    Cocktail cocktail = cocktailRepository.findByName(cocktailName)
                            .orElseThrow(() -> new IllegalArgumentException("Cocktail not by name=%s".formatted(cocktailName)));

                    CocktailIngredient ci = new CocktailIngredient();
                    ci.setIngredient(ingredient);
                    ci.setCocktail(cocktail);
                    ci.setAmount(amount);

                    return ci;
                }).toList();

        ingredient.getCocktails().addAll(newCocktails);
    }

    private void removeAllIngredientRelations(Ingredient ingredient) {
        for (CocktailIngredient ci : ingredient.getCocktails()) {
            ci.setIngredient(null);
            if (ci.getCocktail() != null) {
                ci.getCocktail().getIngredients().remove(ci);
            }
            ci.setCocktail(null);
        }
        ingredient.getCocktails().clear();
    }
}
