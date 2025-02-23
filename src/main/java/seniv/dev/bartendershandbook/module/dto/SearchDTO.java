package seniv.dev.bartendershandbook.module.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import seniv.dev.bartendershandbook.validation.group.GlassGroup;

import java.math.BigDecimal;

@Getter
@Setter
public class SearchDTO {

    @Size(max = 50, message = "max=50 symbols")
    String infix;

    @DecimalMin(value = "0.00", message = "min=0.00")
    @DecimalMax(value = "99.99", message = "max=99.99")
    @Null(groups = GlassGroup.class, message = "Must be null")
    BigDecimal min;

    @DecimalMin(value = "0.00", message = "min=0.00")
    @DecimalMax(value = "99.99", message = "max=99.99")
    @Null(groups = GlassGroup.class, message = "Must be null")
    BigDecimal max;

}
