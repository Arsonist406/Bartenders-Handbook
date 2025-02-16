package seniv.dev.bartendershandbook.controller;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import seniv.dev.bartendershandbook.module.DTO.ingredientDTO.IngredientRequestDTO;
import seniv.dev.bartendershandbook.module.DTO.ingredientDTO.IngredientResponseDTO;
import seniv.dev.bartendershandbook.module.entity.ingredient.Category;
import seniv.dev.bartendershandbook.service.ingredientService.IngredientServiceImpl;
import seniv.dev.bartendershandbook.validation.Create;
import seniv.dev.bartendershandbook.validation.Update;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
@Validated
public class IngredientController {

    private final IngredientServiceImpl ingredientService;

    @Autowired
    public IngredientController(IngredientServiceImpl ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/")
    public List<IngredientResponseDTO> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }

    @GetMapping("/search")
    public List<IngredientResponseDTO> searchIngredients(
            @RequestParam(required = false)
            @Size(min = 1, max = 50, message = "min=1, max=50 symbols")
            String infix,

            @RequestParam(required = false)
            @DecimalMin(value = "0.00", message = "min=0.00")
            @DecimalMax(value = "99.99", message = "max=99.99")
            BigDecimal min,

            @RequestParam(required = false)
            @DecimalMin(value = "0.00", message = "min=0.00")
            @DecimalMax(value = "99.99", message = "max=99.99")
            BigDecimal max,

            @RequestParam(required = false)
            Category category //todo: зробити перевірку на енум
    ) {
        return ingredientService.searchIngredients(infix, min, max, category);
    }

    @GetMapping("/{id}")
    public IngredientResponseDTO getIngredientById(
            @PathVariable Long id
    ) {
        return ingredientService.getIngredientById(id);
    }

    @PostMapping("/")
    public IngredientResponseDTO createIngredient(
            @RequestBody @Validated(Create.class) IngredientRequestDTO ingredient
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
    public IngredientResponseDTO updateIngredientById(
            @PathVariable Long id,
            @RequestBody @Validated(Update.class) IngredientRequestDTO ingredient
    ) {
        return ingredientService.updateIngredientById(id, ingredient);
    }
}
