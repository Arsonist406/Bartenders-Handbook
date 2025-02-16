package seniv.dev.bartendershandbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seniv.dev.bartendershandbook.module.entity.cocktailIngredient.CocktailIngredient;

@Repository
public interface CocktailIngredientRepository extends JpaRepository<CocktailIngredient, Long> {}