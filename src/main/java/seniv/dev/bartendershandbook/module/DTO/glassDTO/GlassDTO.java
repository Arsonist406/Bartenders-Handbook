package seniv.dev.bartendershandbook.module.DTO.glassDTO;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import seniv.dev.bartendershandbook.validation.Create;
import seniv.dev.bartendershandbook.validation.Update;

public class GlassDTO {

    @NotNull(groups = Create.class, message = "Can't be null")
    @Size(min = 2, max = 50, groups = {Create.class, Update.class}, message = "Name length min=2, max=50 symbols")
    private String name;

    @NotNull(groups = Create.class, message = "Can't be null")
    @Size(max = 2000, groups = {Create.class, Update.class}, message = "Description length max=2000 symbols")
    private String description;

    public GlassDTO() {}

    public GlassDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
