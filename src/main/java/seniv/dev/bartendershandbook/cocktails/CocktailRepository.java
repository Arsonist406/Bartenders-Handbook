package seniv.dev.bartendershandbook.cocktails;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seniv.dev.bartendershandbook.glasses.Glass;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface CocktailRepository extends JpaRepository<Cocktail, Long> {
    Optional<Cocktail> findByName(String name);

    List<Cocktail> findByNameContaining(String infix);

    List<Cocktail> findByAbvBetween(
            @DecimalMin("0.00") @DecimalMax("99.99") BigDecimal abvAfter,
            @DecimalMin("0.00") @DecimalMax("99.99") BigDecimal abvBefore
    );

    List<Cocktail> findByGlass(Glass glass);
}
