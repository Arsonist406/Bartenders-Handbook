package seniv.dev.bartendershandbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import seniv.dev.bartendershandbook.module.dto.CocktailDTO;
import seniv.dev.bartendershandbook.module.dto.SearchDTO;
import seniv.dev.bartendershandbook.service.CocktailService;
import seniv.dev.bartendershandbook.validation.group.CreateGroup;
import seniv.dev.bartendershandbook.validation.group.UpdateGroup;

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

    @GetMapping("/")
    public Set<CocktailDTO> searchCocktails(
            @RequestBody @Validated SearchDTO searchDTO
    ) {
        return cocktailService.searchCocktails(searchDTO);
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
