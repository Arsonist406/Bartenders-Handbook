package seniv.dev.bartendershandbook.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import seniv.dev.bartendershandbook.entities.ingredients.Category;
import seniv.dev.bartendershandbook.entities.ingredients.IngredientRequestDTO;
import seniv.dev.bartendershandbook.entities.ingredients.IngredientResponseDTO;
import seniv.dev.bartendershandbook.services.IngredientService;
import seniv.dev.bartendershandbook.validation.Create;
import seniv.dev.bartendershandbook.validation.Update;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients/")
@Validated
public class IngredientController {

    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("all")
    public List<IngredientResponseDTO> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }

    @GetMapping("all/{infix}")
    public List<IngredientResponseDTO> getAllIngredientsThatContainsInfix(
            @PathVariable("infix") String infix
    ) {
        return ingredientService.getAllIngredientsThatContainsInfix(infix);
    }

    @GetMapping("all/{category}")
    public List<IngredientResponseDTO> getAllIngredientsByCategory(
            @PathVariable("category") Category category
    ) {
        return ingredientService.getAllIngredientsByCategory(category);
    }

    @GetMapping("id/{id}")
    public IngredientResponseDTO getIngredientById(
            @PathVariable("id") Long id
    ) {
        return ingredientService.getIngredientById(id);
    }

    @GetMapping("name/{name}")
    public IngredientResponseDTO getIngredientByName(
            @PathVariable ("name") String name
    ) {
        return ingredientService.getIngredientByName(name);
    }

    @PostMapping()
    public IngredientResponseDTO createIngredient(
            @RequestBody @Validated(Create.class) IngredientRequestDTO ingredient
    ) {
        return ingredientService.createIngredient(ingredient);
    }

    @DeleteMapping("id/{id}")
    public void deleteIngredientById(
            @PathVariable("id") Long id
    ) {
        ingredientService.deleteIngredientById(id);
    }

    @DeleteMapping("name/{name}")
    public void deleteIngredientByName(
            @PathVariable("name") String name
    ) {
        ingredientService.deleteIngredientByName(name);
    }

    @PutMapping("{id}")
    public IngredientResponseDTO updateIngredient(
            @PathVariable("id") Long id,
            @RequestBody @Validated(Update.class) IngredientRequestDTO ingredient
    ) {
        return ingredientService.updateIngredient(id, ingredient);
    }
}
