package seniv.dev.bartendershandbook.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import seniv.dev.bartendershandbook.module.cocktail.Cocktail;
import seniv.dev.bartendershandbook.module.glass.Glass;
import seniv.dev.bartendershandbook.module.glassDTO.GlassDTO;
import seniv.dev.bartendershandbook.repository.CocktailRepository;
import seniv.dev.bartendershandbook.repository.GlassRepository;
import seniv.dev.bartendershandbook.service.glassService.GlassServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GlassServiceTest {

    @InjectMocks
    private GlassServiceImpl glassService;

    @Spy
    private GlassRepository glassRepository;

    @Spy
    private CocktailRepository cocktailRepository;

    @Test
    public void testCreateGlass() {

        GlassDTO dto = new GlassDTO("tGDTOName", "-");

        Glass spyGlass = spy(new Glass());
        spyGlass.setName("tGName");

        when(glassRepository.save(any(Glass.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));


        Glass actual = glassService.createGlass(dto);

        assertEquals(dto.getName(), actual.getName());
        assertEquals(dto.getDescription(), actual.getDescription());


        Glass mockGlass = mock(Glass.class);
        when(glassRepository.findByName("tGDTOName")).thenReturn(Optional.of(mockGlass));

        assertThrows(IllegalArgumentException.class, () -> {
            glassService.createGlass(dto);
        });
    }

    @Test
    public void testDeleteGlassById() {

        Cocktail cocktail1 = new Cocktail();
        Cocktail cocktail2 = new Cocktail();
        Cocktail cocktail3 = new Cocktail();

        Glass glass = new Glass("tGName", "-");

        cocktail1.setGlass(glass);
        cocktail2.setGlass(glass);
        cocktail3.setGlass(glass);

        Glass defGlass = new Glass("Default Glass", "default desc");
        when(glassRepository.findByName("Default Glass")).thenReturn(Optional.of(defGlass));

        when(glassRepository.findById(2L)).thenReturn(Optional.of(glass));

        when(cocktailRepository.findByGlass(glass)).thenReturn(List.of(cocktail1, cocktail2, cocktail3));

        glassService.deleteGlassById(2L);

        assertEquals("Default Glass", cocktail1.getGlass().getName());
        assertEquals("Default Glass", cocktail2.getGlass().getName());
        assertEquals("Default Glass", cocktail3.getGlass().getName());
    }

    @Test
    public void testDeleteGlassByName() {

        Cocktail cocktail1 = new Cocktail();
        Cocktail cocktail2 = new Cocktail();
        Cocktail cocktail3 = new Cocktail();

        Glass glass = new Glass("tGName", "-");

        cocktail1.setGlass(glass);
        cocktail2.setGlass(glass);
        cocktail3.setGlass(glass);

        Glass defGlass = new Glass("Default Glass", "default desc");
        when(glassRepository.findByName("Default Glass")).thenReturn(Optional.of(defGlass));

        when(glassRepository.findByName("tGName")).thenReturn(Optional.of(glass));

        when(cocktailRepository.findByGlass(glass)).thenReturn(List.of(cocktail1, cocktail2, cocktail3));

        glassService.deleteGlassByName("tGName");

        assertEquals("Default Glass", cocktail1.getGlass().getName());
        assertEquals("Default Glass", cocktail2.getGlass().getName());
        assertEquals("Default Glass", cocktail3.getGlass().getName());
    }

    @Test
    public void testUpdateGlass() {
        Glass glass = new Glass("tGName", "-");

        when(glassRepository.findById(1L)).thenReturn(Optional.of(glass));

        when(glassRepository.save(any(Glass.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        GlassDTO dto = new GlassDTO("tGDTOName", "---");


        Glass actual = glassService.updateGlass(1L, dto);

        assertEquals(dto.getName(), actual.getName());
        assertEquals(dto.getDescription(), actual.getDescription());


        GlassDTO dto2 = new GlassDTO("tGDTOName2", "---");

        when(glassRepository.findByName(dto2.getName())).thenReturn(Optional.of(glass));

        assertThrows(IllegalArgumentException.class, () -> {
            glassService.updateGlass(1L, dto2);
        });
    }

}
