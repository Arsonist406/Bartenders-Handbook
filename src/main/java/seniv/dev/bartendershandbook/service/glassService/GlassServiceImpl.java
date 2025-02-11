package seniv.dev.bartendershandbook.service.glassService;

import org.springframework.transaction.annotation.Transactional;
import seniv.dev.bartendershandbook.module.glass.Glass;
import seniv.dev.bartendershandbook.module.glassDTO.GlassDTO;

import java.util.List;

public interface GlassServiceImpl {

    List<Glass> getAllGlasses();

    List<Glass> getAllGlassesThatContainsInfix(String infix);

    Glass getGlassById(Long id);

    Glass getGlassByName(String name);

    Glass createGlass(GlassDTO dto);

    void deleteGlassById(Long id);

    void deleteGlassByName(String name);

    @Transactional
    Glass updateGlass(Long id, GlassDTO dto);
}
