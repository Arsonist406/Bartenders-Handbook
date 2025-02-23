package seniv.dev.bartendershandbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import seniv.dev.bartendershandbook.module.dto.IngredientDTO;
import seniv.dev.bartendershandbook.module.dto.SearchDTO;
import seniv.dev.bartendershandbook.service.IngredientService;
import seniv.dev.bartendershandbook.validation.group.CreateGroup;
import seniv.dev.bartendershandbook.validation.group.UpdateGroup;

import java.util.Set;

@RestController
@RequestMapping("/api/ingredients")
@Validated
public class IngredientController {

    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/")
    public Set<IngredientDTO> searchIngredients(
            @RequestBody @Validated SearchDTO searchDTO
    ) {
        return ingredientService.searchIngredients(searchDTO);
    }

    @GetMapping("/{id}")
    public IngredientDTO getIngredientById(
            @PathVariable Long id
    ) {
        return ingredientService.getIngredientById(id);
    }

    @PostMapping("/")
    public IngredientDTO createIngredient(
            @RequestBody @Validated(CreateGroup.class) IngredientDTO ingredient
    ) {
        return ingredientService.createIngredient(ingredient);
    }

    @DeleteMapping("/{id}")
    public void deleteIngredientById(
            @PathVariable Long id
    ) {
        ingredientService.deleteIngredientById(id);
    }

    @PutMapping("/{id}")
    public IngredientDTO updateIngredientById(
            @PathVariable Long id,
            @RequestBody @Validated(UpdateGroup.class) IngredientDTO ingredient
    ) {
        return ingredientService.updateIngredientById(id, ingredient);
    }
}
