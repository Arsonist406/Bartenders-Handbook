package seniv.dev.bartendershandbook.glasses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seniv.dev.bartendershandbook.cocktails.CocktailRequestDTO;
import seniv.dev.bartendershandbook.cocktails.CocktailResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/api/glasses/")
public class GlassController {

    private final GlassService glassService;

    @Autowired
    public GlassController(GlassService glassService) {
        this.glassService = glassService;
    }

    @GetMapping("all")
    public List<Glass> getAllGlasses() {
        return glassService.getAllGlasses();
    }

    @GetMapping("all/{infix}")
    public List<Glass> getAllGlassesThatContainsInfix(@PathVariable("infix") String infix) {
        return glassService.getAllGlassesThatContainsInfix(infix);
    }

    @GetMapping("id/{id}")
    public Glass getGlassById(@PathVariable("id") Long id) {
        return glassService.getGlassById(id);
    }

    @GetMapping("name/{name}")
    public Glass getGlassByName(@PathVariable("name") String name) {
        return glassService.getGlassByName(name);
    }

    @PostMapping()
    public Glass createGlass(@RequestBody Glass glass) {
        return glassService.createGlass(glass);
    }

    @DeleteMapping("id/{id}")
    public void deleteGlassById(@PathVariable("id") Long id) {
        if (id == 1) {
            throw new IllegalArgumentException("Glass with id=1 can't be deleted");
        }
        glassService.deleteGlassById(id);
    }

    @DeleteMapping("name/{name}")
    public void deleteGlassByName(@PathVariable("name") String name) {
        if (name.equals("Default Glass")) {
            throw new IllegalArgumentException("Glass with name='Default Glass' can't be deleted");
        }
        glassService.deleteGlassByName(name);
    }

    @PutMapping("{id}")
    public Glass updateGlass(
            @PathVariable("id") Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description
    ) {
        if (id == 1) {
            throw new IllegalArgumentException("Glass with id=1 can't be changed");
        }
        return glassService.updateGlass(id, name, description);
    }
}
