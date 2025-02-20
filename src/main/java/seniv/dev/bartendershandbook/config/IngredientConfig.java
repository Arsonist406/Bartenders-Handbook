package seniv.dev.bartendershandbook.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import seniv.dev.bartendershandbook.module.entity.Ingredient;
import seniv.dev.bartendershandbook.module.entity.IngredientCategory;
import seniv.dev.bartendershandbook.repository.IngredientRepository;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

@Configuration
public class IngredientConfig {

    @Bean
    @Order(2)
    CommandLineRunner ingredientsCommandLineRunner(IngredientRepository ingredientRepository) {
        return (args) -> {
            var ingredientList = List.of(
                    new Ingredient("Rum", BigDecimal.valueOf(40.0), IngredientCategory.LIQUID, "Rum is a liquor made by fermenting and then distilling sugarcane molasses or sugarcane juice. The distillate, a clear liquid, is often aged in barrels of oak. Rum originated in the Caribbean in the 17th century, but today it is produced in nearly every major sugar-producing region of the world.", new HashSet<>()),
                    new Ingredient("Lime Juice", BigDecimal.valueOf(0.0), IngredientCategory.LIQUID, "Juice extracted from fresh lime", new HashSet<>()),
                    new Ingredient("Mint Leaves", BigDecimal.valueOf(0.0), IngredientCategory.SOLID, "Fresh mint leaves", new HashSet<>()),
                    new Ingredient("Sugar", BigDecimal.valueOf(0.0), IngredientCategory.SOLID, "Granulated sugar", new HashSet<>()),
                    new Ingredient("Soda Water", BigDecimal.valueOf(0.0), IngredientCategory.LIQUID, "Carbonated water", new HashSet<>()),
                    new Ingredient("Horilka", BigDecimal.valueOf(40.0), IngredientCategory.LIQUID, "Vodka is a clear distilled alcoholic beverage. Different varieties originated in Ukraine, Poland, Russia, and Sweden.", new HashSet<>()),
                    new Ingredient("Tomato Juice", BigDecimal.valueOf(0.0), IngredientCategory.LIQUID, "Juice extracted from tomatoes", new HashSet<>()),
                    new Ingredient("Lemon Juice", BigDecimal.valueOf(0.0), IngredientCategory.LIQUID, "Juice extracted from fresh lemon", new HashSet<>()),
                    new Ingredient("Worcestershire Sauce", BigDecimal.valueOf(0.0), IngredientCategory.LIQUID, "Fermented liquid condiment", new HashSet<>()),
                    new Ingredient("Tabasco", BigDecimal.valueOf(0.0), IngredientCategory.LIQUID, "Spicy hot sauce", new HashSet<>()),
                    new Ingredient("Salt", BigDecimal.valueOf(0.0), IngredientCategory.SOLID, "Common table salt", new HashSet<>()),
                    new Ingredient("Pepper", BigDecimal.valueOf(0.0), IngredientCategory.SOLID, "Ground pepper", new HashSet<>()),
                    new Ingredient("Tequila", BigDecimal.valueOf(40.0), IngredientCategory.LIQUID, "Tequila is a distilled beverage made from the blue agave plant.", new HashSet<>()),
                    new Ingredient("Champagne", BigDecimal.valueOf(12.0), IngredientCategory.LIQUID, "Champagne is a sparkling wine originated and produced in the Champagne wine region of France.", new HashSet<>())
            );

            ingredientRepository.saveAll(ingredientList);
        };
    }
}




