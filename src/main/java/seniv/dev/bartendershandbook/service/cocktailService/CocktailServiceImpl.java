package seniv.dev.bartendershandbook.service.cocktailService;

import org.springframework.transaction.annotation.Transactional;
import seniv.dev.bartendershandbook.module.cocktailDTO.CocktailRequestDTO;
import seniv.dev.bartendershandbook.module.cocktailDTO.CocktailResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public interface CocktailServiceImpl {

    List<CocktailResponseDTO> getAllCocktails();

    List<CocktailResponseDTO> getAllCocktailsThatContainsInfix(String infix);

    List<CocktailResponseDTO> getAllCocktailsWithAbvBetween(BigDecimal min, BigDecimal max);

    CocktailResponseDTO getCocktailById(Long id);

    CocktailResponseDTO getCocktailByName(String name);

    CocktailResponseDTO createCocktail(CocktailRequestDTO dto);

    void deleteCocktailById(Long id);

    void deleteCocktailByName(String name);

    @Transactional
    CocktailResponseDTO updateCocktail(Long id, CocktailRequestDTO dto);
}
