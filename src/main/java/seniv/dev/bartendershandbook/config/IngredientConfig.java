package seniv.dev.bartendershandbook.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import seniv.dev.bartendershandbook.module.ingredient.Category;
import seniv.dev.bartendershandbook.module.ingredient.Ingredient;
import seniv.dev.bartendershandbook.repository.IngredientRepository;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class IngredientConfig {

    @Bean
    @Order(2)
    CommandLineRunner ingredientsCommandLineRunner(IngredientRepository ingredientRepository) {
        return (args) -> {
            var ingredientList = List.of(
                    new Ingredient("Rum", 40.0, Category.LIQUID, "Rum is a liquor made by fermenting and then distilling sugarcane molasses or sugarcane juice. The distillate, a clear liquid, is often aged in barrels of oak. Rum originated in the Caribbean in the 17th century, but today it is produced in nearly every major sugar-producing region of the world.", new ArrayList<>()),
                    new Ingredient("Lime Juice", 0.0, Category.LIQUID, "Juice extracted from fresh lime", new ArrayList<>()),
                    new Ingredient("Mint Leaves", 0.0, Category.SOLID, "Fresh mint leaves", new ArrayList<>()),
                    new Ingredient("Sugar", 0.0, Category.SOLID, "Granulated sugar", new ArrayList<>()),
                    new Ingredient("Soda Water", 0.0, Category.LIQUID, "Carbonated water", new ArrayList<>()),
                    new Ingredient("Vodka", 40.0, Category.LIQUID, "Vodka is a clear distilled alcoholic beverage. Different varieties originated in Ukraine, Poland, Russia, and Sweden.", new ArrayList<>()),
                    new Ingredient("Tomato Juice", 0.0, Category.LIQUID, "Juice extracted from tomatoes", new ArrayList<>()),
                    new Ingredient("Lemon Juice", 0.0, Category.LIQUID, "Juice extracted from fresh lemon", new ArrayList<>()),
                    new Ingredient("Worcestershire Sauce", 0.0, Category.LIQUID, "Fermented liquid condiment", new ArrayList<>()),
                    new Ingredient("Tabasco", 0.0, Category.LIQUID, "Spicy hot sauce", new ArrayList<>()),
                    new Ingredient("Salt", 0.0, Category.SOLID, "Common table salt", new ArrayList<>()),
                    new Ingredient("Pepper", 0.0, Category.SOLID, "Ground pepper", new ArrayList<>()),
                    new Ingredient("Tequila", 40.0, Category.LIQUID, "Tequila is a distilled beverage made from the blue agave plant.", new ArrayList<>()),
                    new Ingredient("Champagne", 12.0, Category.LIQUID, "Champagne is a sparkling wine originated and produced in the Champagne wine region of France.", new ArrayList<>())
            );

            ingredientRepository.saveAll(ingredientList);
        };
    }
}




