package seniv.dev.bartendershandbook.controller;

import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import seniv.dev.bartendershandbook.module.dto.GlassDTO;
import seniv.dev.bartendershandbook.service.GlassService;
import seniv.dev.bartendershandbook.validation.group.Create;
import seniv.dev.bartendershandbook.validation.group.Update;

import java.util.Set;

@RestController
@RequestMapping("/api/glasses")
@Validated
public class GlassController {

    private final GlassService glassService;

    @Autowired
    public GlassController(GlassService glassService) {
        this.glassService = glassService;
    }

    @GetMapping("/")
    public Set<GlassDTO> searchGlasses(
            @RequestParam(defaultValue = "")
            @Size(max = 50, message = "max=50 symbols")
            String infix
    ) {
        return glassService.searchGlasses(infix);
    }

    @GetMapping("/{id}")
    public GlassDTO getGlassById(
            @PathVariable Long id
    ) {
        return glassService.getGlassById(id);
    }

    @PostMapping("/")
    public GlassDTO createGlass(
            @RequestBody @Validated(CreateGroup.class) GlassDTO dto
    ) {
        return glassService.createGlass(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteGlassById(
            @PathVariable Long id
    ) {
        glassService.deleteGlassById(id);
    }

    @PutMapping("/{id}")
    public GlassDTO updateGlassById(
            @PathVariable Long id,
            @RequestBody @Validated(UpdateGroup.class) GlassDTO dto
    ) {
        return glassService.updateGlassById(id, dto);
    }
}
