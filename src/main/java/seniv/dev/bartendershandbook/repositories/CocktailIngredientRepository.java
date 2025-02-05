package seniv.dev.bartendershandbook.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seniv.dev.bartendershandbook.entities.cocktails_ingredients.CocktailIngredient;

@Repository
public interface CocktailIngredientRepository extends JpaRepository<CocktailIngredient, Long> {}