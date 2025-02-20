package seniv.dev.bartendershandbook.controller;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import seniv.dev.bartendershandbook.module.dto.IngredientDTO;
import seniv.dev.bartendershandbook.module.entity.IngredientCategory;
import seniv.dev.bartendershandbook.service.IngredientService;
import seniv.dev.bartendershandbook.validation.group.Create;
import seniv.dev.bartendershandbook.validation.group.Update;

import java.math.BigDecimal;
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
    public Set<IngredientDTO> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }

    @GetMapping("/search")
    public Set<IngredientDTO> searchIngredients(
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
            IngredientCategory category
    ) {
        return ingredientService.searchIngredients(infix, min, max, category);
    }

    @GetMapping("/{id}")
    public IngredientDTO getIngredientById(
            @PathVariable Long id
    ) {
        return ingredientService.getIngredientById(id);
    }

    @PostMapping("/")
    public IngredientDTO createIngredient(
            @RequestBody @Validated(Create.class) IngredientDTO ingredient
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
            @RequestBody @Validated(Update.class) IngredientDTO ingredient
    ) {
        return ingredientService.updateIngredientById(id, ingredient);
    }
}
