package seniv.dev.bartendershandbook.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import seniv.dev.bartendershandbook.module.entity.Cocktail;
import seniv.dev.bartendershandbook.module.entity.CocktailIngredient;
import seniv.dev.bartendershandbook.module.entity.Ingredient;
import seniv.dev.bartendershandbook.repository.CocktailIngredientRepository;
import seniv.dev.bartendershandbook.repository.CocktailRepository;
import seniv.dev.bartendershandbook.repository.IngredientRepository;

import java.util.List;

@Configuration
public class CocktailIngredientConfig {

    @Bean
    @Order(4)
    CommandLineRunner cocktailIngredientCommandLineRunner(CocktailRepository cocktailRepository,
                                                          IngredientRepository ingredientRepository,
                                                          CocktailIngredientRepository cocktailIngredientRepository) {
        return (args) -> {
            Cocktail mojito = cocktailRepository.findByName("Mojito")
                    .orElseThrow(() -> new IllegalStateException("Mojito not found"));
            Cocktail bloodyMary = cocktailRepository.findByName("Bloody Mary")
                    .orElseThrow(() -> new IllegalStateException("Bloody Mary not found"));
            Cocktail deathInTheAfternoon = cocktailRepository.findByName("Death in the Afternoon")
                    .orElseThrow(() -> new IllegalStateException("Death in the Afternoon not found"));

            Ingredient rum = ingredientRepository.findByName("Rum")
                    .orElseThrow(() -> new IllegalStateException("Rum not found"));
            Ingredient limeJuice = ingredientRepository.findByName("Lime Juice")
                    .orElseThrow(() -> new IllegalStateException("Lime Juice not found"));
            Ingredient mintLeaves = ingredientRepository.findByName("Mint Leaves")
                    .orElseThrow(() -> new IllegalStateException("Mint Leaves not found"));
            Ingredient sugar = ingredientRepository.findByName("Sugar")
                    .orElseThrow(() -> new IllegalStateException("Sugar not found"));
            Ingredient sodaWater = ingredientRepository.findByName("Soda Water")
                    .orElseThrow(() -> new IllegalStateException("Soda Water not found"));
            Ingredient vodka = ingredientRepository.findByName("Horilka")
                    .orElseThrow(() -> new IllegalStateException("Horilka not found"));
            Ingredient tomatoJuice = ingredientRepository.findByName("Tomato Juice")
                    .orElseThrow(() -> new IllegalStateException("Tomato Juice not found"));
            Ingredient lemonJuice = ingredientRepository.findByName("Lemon Juice")
                    .orElseThrow(() -> new IllegalStateException("Lemon Juice not found"));
            Ingredient worcestershireSauce = ingredientRepository.findByName("Worcestershire Sauce")
                    .orElseThrow(() -> new IllegalStateException("Worcestershire Sauce not found"));
            Ingredient tabasco = ingredientRepository.findByName("Tabasco")
                    .orElseThrow(() -> new IllegalStateException("Tabasco not found"));
            Ingredient salt = ingredientRepository.findByName("Salt")
                    .orElseThrow(() -> new IllegalStateException("Salt not found"));
            Ingredient pepper = ingredientRepository.findByName("Pepper")
                    .orElseThrow(() -> new IllegalStateException("Pepper not found"));
            Ingredient tequila = ingredientRepository.findByName("Tequila")
                    .orElseThrow(() -> new IllegalStateException("Tequila not found"));
            Ingredient champagne = ingredientRepository.findByName("Champagne")
                    .orElseThrow(() -> new IllegalStateException("Champagne not found"));

            CocktailIngredient mojitoRum = new CocktailIngredient(mojito, rum, "50ml");
            CocktailIngredient mojitoLimeJuice = new CocktailIngredient(mojito, limeJuice, "25ml");
            CocktailIngredient mojitoMintLeaves = new CocktailIngredient(mojito, mintLeaves, "6 leaves");
            CocktailIngredient mojitoSugar = new CocktailIngredient(mojito, sugar, "2 tsp");
            CocktailIngredient mojitoSodaWater = new CocktailIngredient(mojito, sodaWater, "Top up");

            CocktailIngredient bloodyMaryVodka = new CocktailIngredient(bloodyMary, vodka, "40ml");
            CocktailIngredient bloodyMaryTomatoJuice = new CocktailIngredient(bloodyMary, tomatoJuice, "100ml");
            CocktailIngredient bloodyMaryLemonJuice = new CocktailIngredient(bloodyMary, lemonJuice, "10ml");
            CocktailIngredient bloodyMaryWorcestershireSauce = new CocktailIngredient(bloodyMary, worcestershireSauce, "2 dashes");
            CocktailIngredient bloodyMaryTabasco = new CocktailIngredient(bloodyMary, tabasco, "1 dash");
            CocktailIngredient bloodyMarySalt = new CocktailIngredient(bloodyMary, salt, "Pinch");
            CocktailIngredient bloodyMaryPepper = new CocktailIngredient(bloodyMary, pepper, "Pinch");

            CocktailIngredient deathInTheAfternoonTequila = new CocktailIngredient(deathInTheAfternoon, tequila, "30ml");
            CocktailIngredient deathInTheAfternoonChampagne = new CocktailIngredient(deathInTheAfternoon, champagne, "Top up");

            cocktailIngredientRepository.saveAll(List.of(
                    mojitoRum, mojitoLimeJuice, mojitoMintLeaves, mojitoSugar, mojitoSodaWater,
                    bloodyMaryVodka, bloodyMaryTomatoJuice, bloodyMaryLemonJuice, bloodyMaryWorcestershireSauce,
                    bloodyMaryTabasco, bloodyMarySalt, bloodyMaryPepper,
                    deathInTheAfternoonTequila, deathInTheAfternoonChampagne));
        };
    }
}

