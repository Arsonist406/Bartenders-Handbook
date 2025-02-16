package seniv.dev.bartendershandbook.service.glassService;

import org.springframework.transaction.annotation.Transactional;
import seniv.dev.bartendershandbook.module.DTO.glassDTO.GlassDTO;
import seniv.dev.bartendershandbook.module.entity.glass.Glass;

import java.util.List;

public interface GlassServiceImpl {

    List<Glass> getAllGlasses();

    List<Glass> searchGlasses(String infix);

    Glass getGlassById(Long id);

    Glass createGlass(GlassDTO dto);

    void deleteGlassById(Long id);

    @Transactional
    Glass updateGlassById(Long id, GlassDTO dto);
}
