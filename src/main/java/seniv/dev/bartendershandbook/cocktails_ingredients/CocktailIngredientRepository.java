package seniv.dev.bartendershandbook.cocktails_ingredients;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CocktailIngredientRepository extends JpaRepository<CocktailIngredient, Long> {}