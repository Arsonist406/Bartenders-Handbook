package seniv.dev.bartendershandbook.service;

import org.springframework.transaction.annotation.Transactional;
import seniv.dev.bartendershandbook.module.dto.CocktailDTO;
import seniv.dev.bartendershandbook.module.entity.Cocktail;
import seniv.dev.bartendershandbook.module.entity.Glass;

import java.math.BigDecimal;
import java.util.Set;

public interface CocktailService {

    Set<CocktailDTO> getAllCocktails();

    Set<CocktailDTO> searchCocktails(String infix, BigDecimal min, BigDecimal max);

    CocktailDTO getCocktailById(Long id);

    CocktailDTO createCocktail(CocktailDTO dto);

    void deleteCocktailById(Long id);

    @Transactional
    CocktailDTO updateCocktailById(Long id, CocktailDTO dto);


    Set<Cocktail> getCocktailsByGlass(Glass glass);

    Set<Cocktail> getCocktailsIn(Set<String> cocktailsName);

    void setCocktailRelations(Cocktail cocktail, CocktailDTO dto);
}
