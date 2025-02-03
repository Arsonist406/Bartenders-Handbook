package seniv.dev.bartendershandbook.glasses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import seniv.dev.bartendershandbook.cocktails.Cocktail;
import seniv.dev.bartendershandbook.cocktails.CocktailRepository;

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

    public Glass createGlass(Glass glass) {
        Long id = glass.getId();
        if (id != null) {
            throw new IllegalArgumentException("Id must be null");
        }

        String name = glass.getName();
        if (name == null) {
            throw new IllegalArgumentException("Name can't be null");
        }
        if (glassRepository.findByName(name).isPresent()) {
            throw new IllegalArgumentException("Name already taken");
        }
        if (name.length() > 50) {
            throw new IllegalArgumentException("Name length must be smaller than 50 symbols");
        }

        String description = glass.getDescription();
        if (description.length() > 2000) {
            throw new IllegalArgumentException("Description length must be smaller than 2000 symbols");
        }

        return glassRepository.save(glass);
    }

    public void deleteGlassById(Long id) {
        Glass glass = glassRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Glass not found by id=%s".formatted(id)));

        List<Cocktail> cocktails = cocktailRepository.findByGlass(glass);
        cocktails.stream().forEach(c ->
                c.setGlass(glassRepository.findByName("Default Glass")
                .orElseThrow(() -> new IllegalArgumentException("Default Glass not found")))
        );

        glassRepository.delete(glass);
    }

    public void deleteGlassByName(String name) {
        Glass glass = glassRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Glass not found by name=%s".formatted(name)));

        List<Cocktail> cocktails = cocktailRepository.findByGlass(glass);
        cocktails.stream().forEach(c ->
                c.setGlass(glassRepository.findByName("Default Glass")
                        .orElseThrow(() -> new IllegalArgumentException("Default Glass not found")))
        );

        glassRepository.delete(glass);
    }

    @Transactional
    public Glass updateGlass(Long id, String name, String description) {
        Glass glass = glassRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Glass not found by id=%s".formatted(id)));

        if (name != null && !name.equals(glass.getName())) {
            if (glassRepository.findByName(name).isPresent()) {
                throw new IllegalArgumentException("Name already taken");
            }
            if (name.length() > 50) {
                throw new IllegalArgumentException("Name length must be smaller than 50 symbols");
            }
            glass.setName(name);
        }

        if (description != null && !description.equals(glass.getDescription())) {
            if (description.length() > 2000) {
                throw new IllegalArgumentException("Description length must be smaller than 2000 symbols");
            }
            glass.setDescription(description);
        }

        return glassRepository.save(glass);
    }
}
