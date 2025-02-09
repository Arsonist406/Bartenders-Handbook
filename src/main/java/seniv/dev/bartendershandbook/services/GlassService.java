package seniv.dev.bartendershandbook.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seniv.dev.bartendershandbook.entities.cocktails.Cocktail;
import seniv.dev.bartendershandbook.entities.glasses.Glass;
import seniv.dev.bartendershandbook.entities.glasses.GlassDTO;
import seniv.dev.bartendershandbook.repositories.CocktailRepository;
import seniv.dev.bartendershandbook.repositories.GlassRepository;

import java.util.List;

@Service
public class GlassService {

    private final GlassRepository glassRepository;
    private final CocktailRepository cocktailRepository;

    @Autowired
    public GlassService(GlassRepository glassRepository, CocktailRepository cocktailRepository) {
        this.glassRepository = glassRepository;
        this.cocktailRepository = cocktailRepository;
    }

    public List<Glass> getAllGlasses() {
        return glassRepository.findAll();
    }

    public List<Glass> getAllGlassesThatContainsInfix(String infix) {
        return glassRepository.findByNameContaining(infix);
    }

    public Glass getGlassById(Long id) {
        return glassRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Glass not found by id=%s".formatted(id)));
    }

    public Glass getGlassByName(String name) {
        return glassRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Glass not found by name=%s".formatted(name)));
    }

    public Glass createGlass(GlassDTO dto) {

        glassRepository.findByName(dto.getName()).ifPresent(temp -> {
            throw new IllegalArgumentException("name=%s is already taken".formatted(dto.getName()));
        });

        Glass glass = new Glass();

        glass.setName(dto.getName());
        glass.setDescription(dto.getDescription());

        return glassRepository.save(glass);
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

    public void deleteGlassByName(String name) {
        Glass glass = glassRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Glass not found by name=%s".formatted(name)));

        List<Cocktail> cocktails = cocktailRepository.findByGlass(glass);

        Glass defaultGlass = glassRepository.findByName("Default Glass")
                .orElseThrow(() -> new IllegalArgumentException("Default Glass not found"));

        cocktails.forEach(c -> c.setGlass(defaultGlass));

        glassRepository.delete(glass);
    }

    @Transactional
    public Glass updateGlass(Long id, GlassDTO dto) {
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

        return glassRepository.save(glass);
    }

}
