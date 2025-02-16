package seniv.dev.bartendershandbook.controller;

import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import seniv.dev.bartendershandbook.module.DTO.glassDTO.GlassDTO;
import seniv.dev.bartendershandbook.service.glassService.GlassServiceImpl;
import seniv.dev.bartendershandbook.validation.Create;
import seniv.dev.bartendershandbook.validation.Update;

import java.util.List;

@RestController
@RequestMapping("/api/glasses")
@Validated
public class GlassController {

    private final GlassServiceImpl glassService;

    @Autowired
    public GlassController(GlassServiceImpl glassService) {
        this.glassService = glassService;
    }

    @GetMapping("/")
    public List<GlassDTO> getAllGlasses() {
        return glassService.getAllGlasses();
    }

    @GetMapping("/search")
    public List<GlassDTO> searchGlasses(
            @RequestParam(required = false)
            @Size(min = 1, max = 50, message = "min=1, max=50 symbols")
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
            @RequestBody @Validated(Create.class) GlassDTO dto
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
            @RequestBody @Validated(Update.class) GlassDTO dto
    ) {
        return glassService.updateGlassById(id, dto);
    }
}
