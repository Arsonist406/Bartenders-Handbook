package seniv.dev.bartendershandbook.controller;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import seniv.dev.bartendershandbook.module.DTO.cocktailDTO.CocktailRequestDTO;
import seniv.dev.bartendershandbook.module.DTO.cocktailDTO.CocktailResponseDTO;
import seniv.dev.bartendershandbook.service.cocktailService.CocktailServiceImpl;
import seniv.dev.bartendershandbook.validation.Create;
import seniv.dev.bartendershandbook.validation.Update;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/cocktails")
@Validated
public class CocktailController {

    private final CocktailServiceImpl cocktailService;

    @Autowired
    public CocktailController(CocktailServiceImpl cocktailService) {
        this.cocktailService = cocktailService;
    }

    @GetMapping("/")
    public List<CocktailResponseDTO> getAllCocktails() {
        return cocktailService.getAllCocktails();
    }

    @GetMapping("/search")
    public List<CocktailResponseDTO> searchCocktails(
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
            BigDecimal max
    ) {
        return cocktailService.searchCocktails(infix, min, max);
    }

    @GetMapping("/{id}")
    public CocktailResponseDTO getCocktailById(
            @PathVariable Long id
    ) {
        return cocktailService.getCocktailById(id);
    }

    @PostMapping("/")
    public CocktailResponseDTO createCocktail(
            @RequestBody @Validated(Create.class) CocktailRequestDTO cocktail
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
    public CocktailResponseDTO updateCocktailById(
            @PathVariable Long id,
            @RequestBody @Validated(Update.class) CocktailRequestDTO cocktail
    ) {
        return cocktailService.updateCocktailById(id, cocktail);
    }
}
