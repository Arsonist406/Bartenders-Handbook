package seniv.dev.bartendershandbook.ingredients;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seniv.dev.bartendershandbook.cocktails.Cocktail;
import seniv.dev.bartendershandbook.glasses.Glass;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    Optional<Ingredient> findByName(String name);

    List<Ingredient> findByNameContaining(String infix);

    List<Ingredient> findByCategory(Category category);
}
