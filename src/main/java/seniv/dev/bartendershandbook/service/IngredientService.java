package seniv.dev.bartendershandbook.service;

import org.springframework.transaction.annotation.Transactional;
import seniv.dev.bartendershandbook.module.dto.IngredientDTO;
import seniv.dev.bartendershandbook.module.entity.Ingredient;
import seniv.dev.bartendershandbook.module.entity.IngredientCategory;

import java.math.BigDecimal;
import java.util.Set;

public interface IngredientService {

    Set<IngredientDTO> getAllIngredients();

    Set<IngredientDTO> searchIngredients(String infix, BigDecimal min, BigDecimal max, IngredientCategory category);

    IngredientDTO getIngredientById(Long id);

    IngredientDTO createIngredient(IngredientDTO dto);

    void deleteIngredientById(Long id);

    @Transactional
    IngredientDTO updateIngredientById(Long id, IngredientDTO dto);


    Set<Ingredient> getIngredientsIn(Set<String> ingredientsNames);

    void setIngredientRelations(Ingredient ingredient, IngredientDTO dto);
}
