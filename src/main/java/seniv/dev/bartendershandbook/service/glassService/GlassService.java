package seniv.dev.bartendershandbook.service.glassService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seniv.dev.bartendershandbook.module.DTO.glassDTO.GlassDTO;
import seniv.dev.bartendershandbook.module.entity.cocktail.Cocktail;
import seniv.dev.bartendershandbook.module.entity.glass.Glass;
import seniv.dev.bartendershandbook.repository.CocktailRepository;
import seniv.dev.bartendershandbook.repository.GlassRepository;

import java.util.List;

@Service
public class GlassService implements GlassServiceImpl {

    private final GlassRepository glassRepository;
    private final CocktailRepository cocktailRepository;    //TODO: репозиторій поміняти на сервіс

    @Autowired
    public GlassService(GlassRepository glassRepository, CocktailRepository cocktailRepository) {
        this.glassRepository = glassRepository;
        this.cocktailRepository = cocktailRepository;
    }

    public List<GlassDTO> getAllGlasses() {
        return glassRepository.findAll().stream().map(
                this::createGlassDTO
        ).toList();
    }

    public List<GlassDTO> searchGlasses(String infix) {
        return glassRepository.findByNameContaining(infix).stream().map(
                this::createGlassDTO
        ).toList();
    }

    public GlassDTO getGlassById(Long id) {
        return createGlassDTO(glassRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Glass not found by id=%s".formatted(id))));
    }

    public GlassDTO createGlass(GlassDTO dto) {

        glassRepository.findByName(dto.getName()).ifPresent(temp -> {
            throw new IllegalArgumentException("name=%s is already taken".formatted(dto.getName()));
        });

        Glass glass = new Glass();

        glass.setName(dto.getName());
        glass.setDescription(dto.getDescription());

        return createGlassDTO(glassRepository.save(glass));
    }

    public void deleteGlassById(Long id) {
        Glass glass = glassRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Glass not found by id=%s".formatted(id)));

        List<Cocktail> cocktails = cocktailRepository.findByGlass(glass);

        Glass defaultGlass = glassRepository.findByName("Default Glass")
                .orElseThrow(() -> new IllegalArgumentException("Default Glass not found"));

        cocktails.forEach(c -> c.setGlass(defaultGlass));

        glassRepository.delete(glass);
    }

    @Transactional
    public GlassDTO updateGlassById(Long id, GlassDTO dto) {
        Glass glass = glassRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Glass not found by id=%s".formatted(id)));

        String name = dto.getName();
        if (name != null && !name.equals(glass.getName())) {
            glassRepository.findByName(name).ifPresent(temp -> {
                throw new IllegalArgumentException("name=%s is already taken".formatted(name));
            });
            glass.setName(name);
        }

        String description = dto.getDescription();
        if (description != null && !description.equals(glass.getDescription())) {
            glass.setDescription(description);
        }

        return createGlassDTO(glassRepository.save(glass));
    }


    private GlassDTO createGlassDTO(Glass glass) {
        return new GlassDTO(
                glass.getName(),
                glass.getDescription()
        );
    }

}
