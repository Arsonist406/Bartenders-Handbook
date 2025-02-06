package seniv.dev.bartendershandbook.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seniv.dev.bartendershandbook.entities.cocktails.Cocktail;
import seniv.dev.bartendershandbook.repositories.CocktailRepository;
import seniv.dev.bartendershandbook.entities.cocktails_ingredients.CocktailIngredient;
import seniv.dev.bartendershandbook.entities.cocktails_ingredients.CocktailIngredientDTO;
import seniv.dev.bartendershandbook.entities.ingredients.*;
import seniv.dev.bartendershandbook.repositories.IngredientRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

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

        if (ingredientRepository.findByName(dto.getName()).isPresent()) {
            throw new IllegalArgumentException("Name already taken");
        }

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
        List<CocktailIngredient> newCocktails = dto.getCocktails().stream()
                .map(cid -> {
                    String cocktailName = cid.getName();
                    String amount = cid.getAmount();

                    Cocktail cocktail = cocktailRepository.findByName(cocktailName)
                            .orElseThrow(() -> new IllegalArgumentException("Cocktail not found by name=%s".formatted(cocktailName)));

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
