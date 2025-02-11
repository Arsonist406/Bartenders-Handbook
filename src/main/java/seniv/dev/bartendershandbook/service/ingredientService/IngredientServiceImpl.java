package seniv.dev.bartendershandbook.service.ingredientService;

import org.springframework.transaction.annotation.Transactional;
import seniv.dev.bartendershandbook.module.ingredient.Category;
import seniv.dev.bartendershandbook.module.ingredientDTO.IngredientRequestDTO;
import seniv.dev.bartendershandbook.module.ingredientDTO.IngredientResponseDTO;

import java.util.List;

public interface IngredientServiceImpl {

    List<IngredientResponseDTO> getAllIngredients();

    List<IngredientResponseDTO> getAllIngredientsThatContainsInfix(String infix);

    List<IngredientResponseDTO> getAllIngredientsByCategory(Category category);

    IngredientResponseDTO getIngredientById(Long id);

    IngredientResponseDTO getIngredientByName(String name);

    IngredientResponseDTO createIngredient(IngredientRequestDTO dto);

    void deleteIngredientById(Long id);

    void deleteIngredientByName(String name);

    @Transactional
    IngredientResponseDTO updateIngredient(Long id, IngredientRequestDTO dto);
}
