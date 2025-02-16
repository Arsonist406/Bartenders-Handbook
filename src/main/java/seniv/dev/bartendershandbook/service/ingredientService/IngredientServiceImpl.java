package seniv.dev.bartendershandbook.service.ingredientService;

import org.springframework.transaction.annotation.Transactional;
import seniv.dev.bartendershandbook.module.DTO.ingredientDTO.IngredientRequestDTO;
import seniv.dev.bartendershandbook.module.DTO.ingredientDTO.IngredientResponseDTO;
import seniv.dev.bartendershandbook.module.entity.ingredient.Category;

import java.math.BigDecimal;
import java.util.List;

public interface IngredientServiceImpl {

    List<IngredientResponseDTO> getAllIngredients();

    List<IngredientResponseDTO> searchIngredients(String infix, BigDecimal min, BigDecimal max, Category category);

    IngredientResponseDTO getIngredientById(Long id);

    IngredientResponseDTO createIngredient(IngredientRequestDTO dto);

    void deleteIngredientById(Long id);

    @Transactional
    IngredientResponseDTO updateIngredientById(Long id, IngredientRequestDTO dto);
}
