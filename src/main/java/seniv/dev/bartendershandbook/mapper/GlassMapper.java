package seniv.dev.bartendershandbook.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import seniv.dev.bartendershandbook.module.dto.GlassDTO;
import seniv.dev.bartendershandbook.module.entity.Glass;

import java.util.HashSet;

@Mapper(componentModel = "spring")
public abstract class GlassMapper {

    public abstract GlassDTO glassToGlassDTO(Glass glass);

    public abstract Glass glassDTOtoGlass(GlassDTO dto);

    @AfterMapping
    protected void createNewHashSet(@MappingTarget Glass glass) {
        if (glass.getCocktails() == null) {
            glass.setCocktails(new HashSet<>());
        }
    }
}
