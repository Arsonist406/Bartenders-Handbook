package seniv.dev.bartendershandbook.repository;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seniv.dev.bartendershandbook.module.entity.Ingredient;
import seniv.dev.bartendershandbook.module.entity.IngredientCategory;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Optional<Ingredient> findByName(String name);

    Set<Ingredient> findByNameIn(Set<String> name);

    Set<Ingredient> findByNameContainingAndAbvBetweenAndCategory(
            @Size(min = 1, max = 50, message = "min=1, max=50 symbols")
            String infix,

            @DecimalMin(value = "0.00", message = "min=0.00")
            @DecimalMax(value = "99.99", message = "max=99.99")
            BigDecimal abvAfter,

            @DecimalMin(value = "0.00", message = "min=0.00")
            @DecimalMax(value = "99.99", message = "max=99.99")
            BigDecimal abvBefore,

            IngredientCategory category
    );
}
