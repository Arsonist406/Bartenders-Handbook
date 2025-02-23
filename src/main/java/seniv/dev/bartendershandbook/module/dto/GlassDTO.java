package seniv.dev.bartendershandbook.module.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import seniv.dev.bartendershandbook.validation.group.CreateGroup;
import seniv.dev.bartendershandbook.validation.group.UpdateGroup;

public record GlassDTO (

        @Null(groups = {CreateGroup.class, UpdateGroup.class}, message = "Must be null")
        Long id,

        @NotNull(groups = CreateGroup.class, message = "Can't be null")
        @Size(min = 2, max = 50, groups = {CreateGroup.class, UpdateGroup.class}, message = "min=2, max=50 symbols")
        String name,

        @NotNull(groups = CreateGroup.class, message = "Can't be null")
        @Size(max = 2000, groups = {CreateGroup.class, UpdateGroup.class}, message = "max=2000 symbols")
        String description

) {}
