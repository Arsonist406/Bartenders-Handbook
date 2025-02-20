package seniv.dev.bartendershandbook.module.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import seniv.dev.bartendershandbook.validation.group.Create;
import seniv.dev.bartendershandbook.validation.group.Update;

public record GlassDTO (

        Long id,

        @NotNull(groups = Create.class, message = "Can't be null")
        @Size(min = 2, max = 50, groups = {Create.class, Update.class}, message = "min=2, max=50 symbols")
        String name,

        @NotNull(groups = Create.class, message = "Can't be null")
        @Size(max = 2000, groups = {Create.class, Update.class}, message = "max=2000 symbols")
        String description

) {}
