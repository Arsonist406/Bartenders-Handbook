package seniv.dev.bartendershandbook.cocktails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/cocktails/")
public class CocktailController {

    private final CocktailService cocktailService;

    @Autowired
    public CocktailController(CocktailService cocktailService) {
        this.cocktailService = cocktailService;
    }

    @GetMapping("all")
    public List<CocktailResponseDTO> getAllCocktails() {
        return cocktailService.getAllCocktails();
    }

    @GetMapping("all/{infix}")
    public List<CocktailResponseDTO> getAllCocktailsThatContainsInfix(@PathVariable("infix") String infix) {
        return cocktailService.getAllCocktailsThatContainsInfix(infix);
    }

    @GetMapping("all/between")
    public List<CocktailResponseDTO> getAllCocktailsWithAbvBetween(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max
    ) {
        return cocktailService.getAllCocktailsWithAbvBetween(min, max);
    }

    @GetMapping("id/{id}")
    public CocktailResponseDTO getCocktailById(@PathVariable("id") Long id) {
        return cocktailService.getCocktailById(id);
    }

    @GetMapping("name/{name}")
    public CocktailResponseDTO getCocktailByName(@PathVariable("name") String name) {
        return cocktailService.getCocktailByName(name);
    }

    @PostMapping()
    public CocktailResponseDTO createCocktail(@RequestBody CocktailRequestDTO cocktail) {
        return cocktailService.createCocktail(cocktail);
    }

    @DeleteMapping("id/{id}")
    public void deleteCocktailById(@PathVariable("id") Long id) {
        cocktailService.deleteCocktailById(id);
    }

    @DeleteMapping("name/{name}")
    public void deleteCocktailByName(@PathVariable("name") String name) {
        cocktailService.deleteCocktailByName(name);
    }

    @PutMapping("{id}")
    public CocktailResponseDTO updateCocktail(
            @PathVariable("id") Long id,
            @RequestBody CocktailRequestDTO cocktail
    ) {
        return cocktailService.updateCocktail(id, cocktail);
    }
}
