package seniv.dev.bartendershandbook.service.glassService;

import org.springframework.transaction.annotation.Transactional;
import seniv.dev.bartendershandbook.module.DTO.glassDTO.GlassDTO;
import seniv.dev.bartendershandbook.module.entity.glass.Glass;

import java.util.List;

public interface GlassServiceImpl {

    List<GlassDTO> getAllGlasses();

    List<GlassDTO> searchGlasses(String infix);

    GlassDTO getGlassById(Long id);

    GlassDTO createGlass(GlassDTO dto);

    void deleteGlassById(Long id);

    @Transactional
    GlassDTO updateGlassById(Long id, GlassDTO dto);
}
