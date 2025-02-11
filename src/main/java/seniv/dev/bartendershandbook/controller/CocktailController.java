package seniv.dev.bartendershandbook.controller;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import seniv.dev.bartendershandbook.module.cocktailDTO.CocktailRequestDTO;
import seniv.dev.bartendershandbook.module.cocktailDTO.CocktailResponseDTO;
import seniv.dev.bartendershandbook.module.validation.Create;
import seniv.dev.bartendershandbook.module.validation.Update;
import seniv.dev.bartendershandbook.service.cocktailService.CocktailServiceImpl;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/cocktails/")
public class CocktailController {

    private final CocktailServiceImpl cocktailService;

    @Autowired
    public CocktailController(CocktailServiceImpl cocktailService) {
        this.cocktailService = cocktailService;
    }

    @GetMapping("all")
    public List<CocktailResponseDTO> getAllCocktails() {
        return cocktailService.getAllCocktails();
    }

    @GetMapping("all/{infix}")
    public List<CocktailResponseDTO> getAllCocktailsThatContainsInfix(
            @PathVariable("infix") String infix
    ) {
        return cocktailService.getAllCocktailsThatContainsInfix(infix);
    }

    @GetMapping("all/between")
    public List<CocktailResponseDTO> getAllCocktailsWithAbvBetween(
            @RequestParam @DecimalMin(value = "0.00", message = "Abv min=0.00") BigDecimal min,
            @RequestParam @DecimalMax(value = "99.99", message = "Abv max=99.99") BigDecimal max
    ) {
        return cocktailService.getAllCocktailsWithAbvBetween(min, max);
    }

    @GetMapping("id/{id}")
    public CocktailResponseDTO getCocktailById(
            @PathVariable("id") Long id
    ) {
        return cocktailService.getCocktailById(id);
    }

    @GetMapping("name/{name}")
    public CocktailResponseDTO getCocktailByName(
            @PathVariable("name") String name
    ) {
        return cocktailService.getCocktailByName(name);
    }

    @PostMapping()
    public CocktailResponseDTO createCocktail(
            @RequestBody @Validated(Create.class) CocktailRequestDTO cocktail
    ) {
        return cocktailService.createCocktail(cocktail);
    }

    @DeleteMapping("id/{id}")
    public void deleteCocktailById(
            @PathVariable("id") Long id
    ) {
        cocktailService.deleteCocktailById(id);
    }

    @DeleteMapping("name/{name}")
    public void deleteCocktailByName(
            @PathVariable("name") String name
    ) {
        cocktailService.deleteCocktailByName(name);
    }

    @PutMapping("{id}")
    public CocktailResponseDTO updateCocktail(
            @PathVariable("id") Long id,
            @RequestBody @Validated(Update.class) CocktailRequestDTO cocktail
    ) {
        return cocktailService.updateCocktail(id, cocktail);
    }
}
