package seniv.dev.bartendershandbook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seniv.dev.bartendershandbook.exceptionHandler.exception.IsAlreadyTakenException;
import seniv.dev.bartendershandbook.exceptionHandler.exception.NotFoundByException;
import seniv.dev.bartendershandbook.mapper.GlassMapper;
import seniv.dev.bartendershandbook.module.dto.GlassDTO;
import seniv.dev.bartendershandbook.module.dto.SearchDTO;
import seniv.dev.bartendershandbook.module.entity.Glass;
import seniv.dev.bartendershandbook.repository.GlassRepository;
import seniv.dev.bartendershandbook.service.CocktailService;
import seniv.dev.bartendershandbook.service.GlassService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GlassServiceImpl implements GlassService {

    private final GlassRepository glassRepository;
    private final CocktailService cocktailService;
    private final GlassMapper glassMapper;

    @Autowired
    public GlassServiceImpl(
            GlassRepository glassRepository,
            @Lazy CocktailService cocktailService,
            GlassMapper glassMapper
    ) {
        this.glassRepository = glassRepository;
        this.cocktailService = cocktailService;
        this.glassMapper = glassMapper;
    }

    public Set<GlassDTO> searchGlasses(SearchDTO searchDTO) {
        if (searchDTO.getInfix() == null) {
            searchDTO.setInfix("");
        }

        return glassRepository.findByNameContaining(searchDTO.getInfix())
                .stream()
                .map(glassMapper::glassToGlassDTO)
                .collect(Collectors.toSet());
    }

    public GlassDTO getGlassById(Long id) {
        return glassMapper.glassToGlassDTO(glassRepository.findById(id)
                .orElseThrow(() -> new NotFoundByException(Glass.class, "id", id)));
    }

    public GlassDTO createGlass(GlassDTO dto) {

        glassRepository.findByName(dto.name()).ifPresent(_ -> {
            throw new IsAlreadyTakenException("name", dto.name());
        });

        return glassMapper.glassToGlassDTO(glassRepository.save(glassMapper.glassDTOtoGlass(dto)));
    }

    public void deleteGlassById(Long id) {
        Glass glass = glassRepository.findById(id)
                .orElseThrow(() -> new NotFoundByException(Glass.class, "id", id));

        cocktailService.getCocktailsByGlass(glass)
                .forEach(cocktail -> cocktail.getGlasses().remove(glass));

        glassRepository.delete(glass);
    }

    @Transactional
    public GlassDTO updateGlassById(Long id, GlassDTO dto) {
        Glass glass = glassRepository.findById(id)
                .orElseThrow(() -> new NotFoundByException(Glass.class, "id", id));

        String newName = dto.name();
        String oldName = glass.getName();
        if (newName != null && !newName.equals(oldName)) {
            glassRepository.findByName(newName).ifPresent(_ -> {
                throw new IsAlreadyTakenException("name", newName);
            });
            glass.setName(newName);
        }

        String newDescription = dto.description();
        String oldDescription = glass.getDescription();
        if (newDescription != null && !newDescription.equals(oldDescription)) {
            glass.setDescription(newDescription);
        }

        return glassMapper.glassToGlassDTO(glassRepository.save(glass));
    }


    public Glass getGlassByName(String name) {
        return glassRepository.findByName(name)
                .orElseThrow(() -> new NotFoundByException(Glass.class, "name", name));
    }

}
