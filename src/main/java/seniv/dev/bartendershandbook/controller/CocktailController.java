package seniv.dev.bartendershandbook.controller;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import seniv.dev.bartendershandbook.module.dto.CocktailDTO;
import seniv.dev.bartendershandbook.service.CocktailService;
import seniv.dev.bartendershandbook.validation.group.Create;
import seniv.dev.bartendershandbook.validation.group.Update;

import java.math.BigDecimal;
import java.util.Set;

@RestController
@RequestMapping("/api/cocktails")
@Validated
public class CocktailController {

    private final CocktailService cocktailService;

    @Autowired
    public CocktailController(CocktailService cocktailService) {
        this.cocktailService = cocktailService;
    }

    //todo: searchDTO
    @GetMapping("/")
    public Set<CocktailDTO> searchCocktails(
            @RequestParam(defaultValue = "")
            @Size(max = 50, message = "max=50 symbols")
            String infix,

            @RequestParam(defaultValue = "0.00")
            @DecimalMin(value = "0.00", message = "min=0.00")
            @DecimalMax(value = "99.99", message = "max=99.99")
            BigDecimal min,

            @RequestParam(defaultValue = "99.99")
            @DecimalMin(value = "0.00", message = "min=0.00")
            @DecimalMax(value = "99.99", message = "max=99.99")
            BigDecimal max
    ) {
        return cocktailService.searchCocktails(infix, min, max);
    }

    @GetMapping("/{id}")
    public CocktailDTO getCocktailById(
            @PathVariable Long id
    ) {
        return cocktailService.getCocktailById(id);
    }

    @PostMapping("/")
    public CocktailDTO createCocktail(
            @RequestBody @Validated(CreateGroup.class) CocktailDTO cocktail
    ) {
        return cocktailService.createCocktail(cocktail);
    }

    @DeleteMapping("/{id}")
    public void deleteCocktailById(
            @PathVariable Long id
    ) {
        cocktailService.deleteCocktailById(id);
    }

    @PutMapping("/{id}")
    public CocktailDTO updateCocktailById(
            @PathVariable Long id,
            @RequestBody @Validated(UpdateGroup.class) CocktailDTO cocktail
    ) {
        return cocktailService.updateCocktailById(id, cocktail);
    }
}
