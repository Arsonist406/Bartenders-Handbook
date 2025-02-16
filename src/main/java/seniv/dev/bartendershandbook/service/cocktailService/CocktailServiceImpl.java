package seniv.dev.bartendershandbook.service.cocktailService;

import org.springframework.transaction.annotation.Transactional;
import seniv.dev.bartendershandbook.module.DTO.cocktailDTO.CocktailRequestDTO;
import seniv.dev.bartendershandbook.module.DTO.cocktailDTO.CocktailResponseDTO;
import seniv.dev.bartendershandbook.module.entity.cocktail.Cocktail;
import seniv.dev.bartendershandbook.module.entity.glass.Glass;

import java.math.BigDecimal;
import java.util.List;

public interface CocktailServiceImpl {

    List<CocktailResponseDTO> getAllCocktails();

    List<CocktailResponseDTO> searchCocktails(String infix, BigDecimal min, BigDecimal max);

    CocktailResponseDTO getCocktailById(Long id);

    CocktailResponseDTO createCocktail(CocktailRequestDTO dto);

    void deleteCocktailById(Long id);

    @Transactional
    CocktailResponseDTO updateCocktailById(Long id, CocktailRequestDTO dto);


    List<Cocktail> getCocktailsByGlass(Glass glass);

    List<Cocktail> getCocktailsIn(List<String> cocktailsName);
}
