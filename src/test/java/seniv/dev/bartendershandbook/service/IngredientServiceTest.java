package seniv.dev.bartendershandbook.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import seniv.dev.bartendershandbook.module.cocktail.Cocktail;
import seniv.dev.bartendershandbook.module.cocktails_ingredient.CocktailIngredient;
import seniv.dev.bartendershandbook.module.cocktails_ingredientDTO.CocktailIngredientDTO;
import seniv.dev.bartendershandbook.module.ingredient.Ingredient;
import seniv.dev.bartendershandbook.module.ingredientDTO.IngredientRequestDTO;
import seniv.dev.bartendershandbook.module.ingredientDTO.IngredientResponseDTO;
import seniv.dev.bartendershandbook.repository.CocktailRepository;
import seniv.dev.bartendershandbook.repository.IngredientRepository;
import seniv.dev.bartendershandbook.service.ingredientService.IngredientServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static seniv.dev.bartendershandbook.module.ingredient.Category.LIQUID;
import static seniv.dev.bartendershandbook.module.ingredient.Category.SOLID;

@ExtendWith(MockitoExtension.class)
public class IngredientServiceTest {

    @InjectMocks
    private IngredientServiceImpl ingredientService;

    @Spy
    private IngredientRepository ingredientRepository;
    @Spy
    private CocktailRepository cocktailRepository;

    @Test
    public void testCreateIngredient() {

        Cocktail cocktail1 = new Cocktail();
        Cocktail cocktail2 = new Cocktail();
        Cocktail cocktail3 = new Cocktail();

        cocktail1.setName("tCName1");
        cocktail2.setName("tCName2");
        cocktail3.setName("tCName3");

        when(cocktailRepository.findByNameIn(anyList())).thenReturn(List.of(cocktail1, cocktail2, cocktail3));

        List<CocktailIngredientDTO> cocktailsDTO = new ArrayList<>();

        cocktailsDTO.add(new CocktailIngredientDTO(cocktail1.getName(), "-"));
        cocktailsDTO.add(new CocktailIngredientDTO(cocktail2.getName(), "-"));
        cocktailsDTO.add(new CocktailIngredientDTO(cocktail3.getName(), "-"));


        IngredientRequestDTO dto = new IngredientRequestDTO(
                "tIName",
                10.05,
                SOLID,
                "tIDescription",
                cocktailsDTO
        );

        when(ingredientRepository.save(any(Ingredient.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(ingredientRepository.findByName(dto.getName())).thenReturn(Optional.empty());

        IngredientResponseDTO actual = ingredientService.createIngredient(dto);

        assertEquals(dto.getName(), actual.getName());
        assertEquals(dto.getAbv(), actual.getAbv());
        assertEquals(dto.getCategory(), actual.getCategory());
        assertEquals(dto.getDescription(), actual.getDescription());
        assertEquals(dto.getCocktails().get(0).getName(), actual.getCocktails().get(0).getName());
        assertEquals(dto.getCocktails().get(1).getName(), actual.getCocktails().get(1).getName());
        assertEquals(dto.getCocktails().get(2).getName(), actual.getCocktails().get(2).getName());


        Ingredient mockIngredient = mock(Ingredient.class);
        when(ingredientRepository.findByName(dto.getName())).thenReturn(Optional.of(mockIngredient));

        assertThrows(IllegalArgumentException.class, () -> {
            ingredientService.createIngredient(dto);
        });
    }

    @Test
    public void testUpdateIngredient() {

        Cocktail cocktail1 = new Cocktail();
        Cocktail cocktail2 = new Cocktail();
        Cocktail cocktail3 = new Cocktail();

        cocktail1.setName("tCName1");
        cocktail2.setName("tCName2");
        cocktail3.setName("tCName3");

        when(cocktailRepository.findByNameIn(anyList())).thenReturn(List.of(cocktail1, cocktail2, cocktail3));


        List<CocktailIngredientDTO> cocktailsDTO = new ArrayList<>();

        cocktailsDTO.add(new CocktailIngredientDTO(cocktail1.getName(), "-"));
        cocktailsDTO.add(new CocktailIngredientDTO(cocktail2.getName(), "-"));
        cocktailsDTO.add(new CocktailIngredientDTO(cocktail3.getName(), "-"));


        IngredientRequestDTO dto = new IngredientRequestDTO(
                "tIDTOName",
                10.05,
                SOLID,
                "tIDescription",
                cocktailsDTO
        );

        Ingredient ingredient = new Ingredient();
        ingredient.setName("tIName");
        ingredient.setAbv(10.0);
        ingredient.setCategory(LIQUID);
        ingredient.setDescription("---");

        List<CocktailIngredient> cocktails = new ArrayList<>();
        cocktails.add(new CocktailIngredient(cocktail1, ingredient, "---"));
        cocktails.add(new CocktailIngredient(cocktail2, ingredient, "---"));
        cocktails.add(new CocktailIngredient(cocktail3, ingredient, "---"));

        ingredient.setCocktails(cocktails);

        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient));

        when(ingredientRepository.save(any(Ingredient.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        IngredientResponseDTO actual = ingredientService.updateIngredient(1L, dto);

        assertEquals(dto.getName(), actual.getName());
        assertEquals(dto.getAbv(), actual.getAbv());
        assertEquals(dto.getCategory(), actual.getCategory());
        assertEquals(dto.getDescription(), actual.getDescription());
        assertEquals(dto.getCocktails().get(0).getName(), actual.getCocktails().get(0).getName());
        assertEquals(dto.getCocktails().get(1).getName(), actual.getCocktails().get(1).getName());
        assertEquals(dto.getCocktails().get(2).getName(), actual.getCocktails().get(2).getName());

        IngredientRequestDTO dto2 = new IngredientRequestDTO(
                "tIName2",
                10.05,
                SOLID,
                "tIDescription",
                cocktailsDTO
        );

        Ingredient mockIngredient = mock(Ingredient.class);
        when(ingredientRepository.findByName(dto2.getName())).thenReturn(Optional.of(mockIngredient));

        assertThrows(IllegalArgumentException.class, () -> {
            ingredientService.updateIngredient(1L, dto2);
        });
    }
}
