package seniv.dev.bartendershandbook.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import seniv.dev.bartendershandbook.module.entity.cocktail.Cocktail;
import seniv.dev.bartendershandbook.module.DTO.cocktailDTO.CocktailRequestDTO;
import seniv.dev.bartendershandbook.module.DTO.cocktailDTO.CocktailResponseDTO;
import seniv.dev.bartendershandbook.module.entity.cocktailIngredient.CocktailIngredient;
import seniv.dev.bartendershandbook.module.DTO.cocktails_ingredientDTO.CocktailIngredientDTO;
import seniv.dev.bartendershandbook.module.entity.glass.Glass;
import seniv.dev.bartendershandbook.module.DTO.glassDTO.GlassDTO;
import seniv.dev.bartendershandbook.module.entity.ingredient.Ingredient;
import seniv.dev.bartendershandbook.repository.CocktailRepository;
import seniv.dev.bartendershandbook.repository.GlassRepository;
import seniv.dev.bartendershandbook.repository.IngredientRepository;
import seniv.dev.bartendershandbook.service.cocktailService.CocktailServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CocktailServiceTest {

    @InjectMocks
    private CocktailServiceImpl cocktailService;

    @Spy
    private CocktailRepository cocktailRepository;
    @Spy
    private GlassRepository glassRepository;
    @Spy
    private IngredientRepository ingredientRepository;


    @Test
    public void testCreateCocktail() {

        when(cocktailRepository.save(any(Cocktail.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Glass spyGlass = spy(new Glass());
        spyGlass.setName("tGName");
        when(glassRepository.findByName("tGName")).thenReturn(Optional.of(spyGlass));


        Ingredient ingredient1 = new Ingredient();
        Ingredient ingredient2 = new Ingredient();
        Ingredient ingredient3 = new Ingredient();

        ingredient1.setName("tIName1");
        ingredient2.setName("tIName2");
        ingredient3.setName("tIName3");

        when(ingredientRepository.findByNameIn(anyList())).thenReturn(List.of(ingredient1, ingredient2, ingredient3));


        GlassDTO spyGlassDTO = spy(new GlassDTO());
        spyGlassDTO.setName("tGName");


        CocktailIngredientDTO spyCI1 = spy(new CocktailIngredientDTO());
        CocktailIngredientDTO spyCI2 = spy(new CocktailIngredientDTO());
        CocktailIngredientDTO spyCI3 = spy(new CocktailIngredientDTO());

        spyCI1.setAmount("tAmount");
        spyCI2.setAmount("tAmount");
        spyCI3.setAmount("tAmount");

        spyCI1.setName("tIName1");
        spyCI2.setName("tIName2");
        spyCI3.setName("tIName3");

        List<CocktailIngredientDTO> spyIngredients = new ArrayList<>();
        spyIngredients.add(spyCI1);
        spyIngredients.add(spyCI2);
        spyIngredients.add(spyCI3);


        CocktailRequestDTO dto = new CocktailRequestDTO(
                "tName",
                100,
                10.0,
                spyGlassDTO,
                "tDescription",
                "tRecipe",
                spyIngredients
        );

        CocktailResponseDTO actual = cocktailService.createCocktail(dto);

        assertEquals(dto.getName(), actual.getName());
        assertEquals(dto.getVolume(), actual.getVolume());
        assertEquals(dto.getAbv(), actual.getAbv());
        assertEquals(dto.getGlass().getName(), actual.getGlass());
        assertEquals(dto.getDescription(), actual.getDescription());
        assertEquals(dto.getRecipe(), actual.getRecipe());
        assertEquals(dto.getIngredients().get(0).getName(), actual.getIngredients().get(0).getName());
        assertEquals(dto.getIngredients().get(1).getName(), actual.getIngredients().get(1).getName());
        assertEquals(dto.getIngredients().get(2).getName(), actual.getIngredients().get(2).getName());

        Cocktail mockCocktail = mock(Cocktail.class);
        when(cocktailRepository.findByName("tName")).thenReturn(Optional.of(mockCocktail));

        assertThrows(IllegalArgumentException.class, () -> {
            cocktailService.createCocktail(dto);
        });
    }

    @Test
    public void testUpdateCocktailById() {
        Cocktail cocktail = new Cocktail();

        cocktail.setName("tCNameBefore");
        cocktail.setVolume(1005);
        cocktail.setAbv(10.05);

        Glass spyGlass1 = spy(new Glass());
        spyGlass1.setName("tGName");

        cocktail.setGlass(spyGlass1);

        cocktail.setDescription("tDescriptionBefore");
        cocktail.setRecipe("tRecipeBefore");
        cocktail.getIngredients().addAll(List.of(
                new CocktailIngredient(cocktail, mock(Ingredient.class), "-"),
                new CocktailIngredient(cocktail, mock(Ingredient.class), "-"),
                new CocktailIngredient(cocktail, mock(Ingredient.class), "-"))
        );

        when(cocktailRepository.findById(1L)).thenReturn(Optional.of(cocktail));

        when(cocktailRepository.save(any(Cocktail.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));


        Ingredient ingredient1 = new Ingredient();
        Ingredient ingredient2 = new Ingredient();
        Ingredient ingredient3 = new Ingredient();

        ingredient1.setName("tIName1");
        ingredient2.setName("tIName2");
        ingredient3.setName("tIName3");

        when(ingredientRepository.findByNameIn(anyList())).thenReturn(List.of(ingredient1, ingredient2, ingredient3));


        GlassDTO spyGlassDTO = spy(new GlassDTO());
        Glass spyGlass2 = spy(new Glass());

        spyGlassDTO.setName("tGDTOName");
        spyGlass2.setName("tGDTOName");

        when(glassRepository.findByName("tGDTOName")).thenReturn(Optional.of(spyGlass2));


        CocktailIngredientDTO spyCI1 = spy(new CocktailIngredientDTO());
        CocktailIngredientDTO spyCI2 = spy(new CocktailIngredientDTO());
        CocktailIngredientDTO spyCI3 = spy(new CocktailIngredientDTO());

        spyCI1.setAmount("tAmount");
        spyCI2.setAmount("tAmount");
        spyCI3.setAmount("tAmount");

        spyCI1.setName("tIName1");
        spyCI2.setName("tIName2");
        spyCI3.setName("tIName3");

        List<CocktailIngredientDTO> spyIngredients = new ArrayList<>();
        spyIngredients.add(spyCI1);
        spyIngredients.add(spyCI2);
        spyIngredients.add(spyCI3);


        CocktailRequestDTO dto = new CocktailRequestDTO(
                "tName",
                100,
                10.0,
                spyGlassDTO,
                "tDescription",
                "tRecipe",
                spyIngredients
        );

        CocktailResponseDTO actual = cocktailService.updateCocktailById(1L, dto);

        assertEquals(dto.getName(), actual.getName());
        assertEquals(dto.getVolume(), actual.getVolume());
        assertEquals(dto.getAbv(), actual.getAbv());
        assertEquals(dto.getGlass().getName(), actual.getGlass());
        assertEquals(dto.getDescription(), actual.getDescription());
        assertEquals(dto.getRecipe(), actual.getRecipe());
        assertEquals(dto.getIngredients().get(0).getName(), actual.getIngredients().get(0).getName());
        assertEquals(dto.getIngredients().get(1).getName(), actual.getIngredients().get(1).getName());
        assertEquals(dto.getIngredients().get(2).getName(), actual.getIngredients().get(2).getName());


        CocktailRequestDTO dto2 = new CocktailRequestDTO(
                "tName2",
                100,
                10.0,
                spyGlassDTO,
                "tDescription",
                "tRecipe",
                spyIngredients
        );

        Cocktail mockCocktail = mock(Cocktail.class);
        when(cocktailRepository.findByName("tName2")).thenReturn(Optional.of(mockCocktail));

        assertThrows(IllegalArgumentException.class, () -> {
            cocktailService.updateCocktailById(1L, dto2);
        });
    }
}
