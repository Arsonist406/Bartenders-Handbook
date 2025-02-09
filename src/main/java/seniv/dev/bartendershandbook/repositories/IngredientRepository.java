package seniv.dev.bartendershandbook.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seniv.dev.bartendershandbook.entities.ingredients.Category;
import seniv.dev.bartendershandbook.entities.ingredients.Ingredient;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    Optional<Ingredient> findByName(String name);

    List<Ingredient> findByNameIn(List<String> name);

    List<Ingredient> findByNameContaining(String infix);

    List<Ingredient> findByCategory(Category category);
}
