package seniv.dev.bartendershandbook.repository;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seniv.dev.bartendershandbook.module.entity.cocktail.Cocktail;
import seniv.dev.bartendershandbook.module.entity.glass.Glass;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CocktailRepository extends JpaRepository<Cocktail, Long> {

    Optional<Cocktail> findByName(String name);

    List<Cocktail> findByNameIn(List<String> name);

    List<Cocktail> findByNameContainingAndAbvBetween(
            @Size(min = 1, max = 50, message = "min=1, max=50 symbols")
            String infix,

            @DecimalMin(value = "0.00", message = "min=0.00")
            @DecimalMax(value = "99.99", message = "max=99.99")
            BigDecimal abvAfter,

            @DecimalMin(value = "0.00", message = "min=0.00")
            @DecimalMax(value = "99.99", message = "max=99.99")
            BigDecimal abvBefore
    );

    List<Cocktail> findByGlassesContaining(Set<Glass> glasses);
}
