package seniv.dev.bartendershandbook.service;

import org.springframework.transaction.annotation.Transactional;
import seniv.dev.bartendershandbook.module.dto.GlassDTO;
import seniv.dev.bartendershandbook.module.dto.SearchDTO;
import seniv.dev.bartendershandbook.module.entity.Glass;

import java.util.Set;

public interface GlassService {

    Set<GlassDTO> searchGlasses(SearchDTO searchDTO);

    GlassDTO getGlassById(Long id);

    GlassDTO createGlass(GlassDTO dto);

    void deleteGlassById(Long id);

    @Transactional
    GlassDTO updateGlassById(Long id, GlassDTO dto);


    Glass getGlassByName(String name);
}
