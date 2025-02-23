package seniv.dev.bartendershandbook.service;

import seniv.dev.bartendershandbook.module.dto.IngredientDTO;
import seniv.dev.bartendershandbook.module.dto.SearchDTO;
import seniv.dev.bartendershandbook.module.entity.Ingredient;

import java.util.Set;

public interface IngredientService {

    Set<IngredientDTO> searchIngredients(SearchDTO searchDTO);

    IngredientDTO getIngredientById(Long id);

    IngredientDTO createIngredient(IngredientDTO dto);

    void deleteIngredientById(Long id);

    IngredientDTO updateIngredientById(Long id, IngredientDTO dto);


    Set<Ingredient> getIngredientsIn(Set<String> ingredientsNames);

    void setIngredientRelations(Ingredient ingredient, IngredientDTO dto);
}
