package seniv.dev.bartendershandbook.repository;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seniv.dev.bartendershandbook.module.entity.Cocktail;
import seniv.dev.bartendershandbook.module.entity.Glass;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CocktailRepository extends JpaRepository<Cocktail, Long> {

    Optional<Cocktail> findByName(String name);

    Set<Cocktail> findByNameIn(Set<String> name);

    Set<Cocktail> findByNameContainingAndAbvBetween(
            @Size(min = 1, max = 50, message = "min=1, max=50 symbols")
            String infix,

            @DecimalMin(value = "0.00", message = "min=0.00")
            @DecimalMax(value = "99.99", message = "max=99.99")
            BigDecimal abvAfter,

            @DecimalMin(value = "0.00", message = "min=0.00")
            @DecimalMax(value = "99.99", message = "max=99.99")
            BigDecimal abvBefore
    );

    Set<Cocktail> findByGlassesContaining(Set<Glass> glasses);
}
