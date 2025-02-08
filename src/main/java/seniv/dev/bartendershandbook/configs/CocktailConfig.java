package seniv.dev.bartendershandbook.configs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import seniv.dev.bartendershandbook.entities.cocktails.Cocktail;
import seniv.dev.bartendershandbook.entities.glasses.Glass;
import seniv.dev.bartendershandbook.repositories.CocktailRepository;
import seniv.dev.bartendershandbook.repositories.GlassRepository;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CocktailConfig {

    @Bean
    @Order(3)
    CommandLineRunner cocktailCommandLineRunner(
            CocktailRepository cocktailRepository,
            GlassRepository glassRepository
    ) {
        return (args) -> {
            Glass mojitoGlass = glassRepository.findByName("Highball")
                    .orElseThrow(() -> new IllegalStateException("Glass 'Highball' not found"));
            Glass bloodyMaryGlass = glassRepository.findByName("Highball")
                    .orElseThrow(() -> new IllegalStateException("Glass 'Highball' not found"));
            Glass deathGlass = glassRepository.findByName("Martini")
                    .orElseThrow(() -> new IllegalStateException("Glass 'Martini' not found"));

            Cocktail mojito = new Cocktail("Mojito", 300, 14.0, mojitoGlass, "Refreshing rum-based cocktail", "Muddle mint leaves, lime, and sugar in a glass. Add rum and top with soda water. Serve with ice and garnish with mint.", new ArrayList<>());
            Cocktail bloodyMary = new Cocktail("Bloody Mary", 400, 18.0, bloodyMaryGlass, "Spicy and savory vodka cocktail", "Mix vodka, tomato juice, lemon juice, Worcestershire sauce, Tabasco, salt, and pepper in a shaker. Pour into a glass filled with ice and garnish with celery.", new ArrayList<>());
            Cocktail deathInTheAfternoon = new Cocktail("Death in the Afternoon", 200, 12.07, deathGlass, "A refreshing champagne cocktail", "Pour tequila into a glass and top with champagne. Stir gently and garnish with a lime wedge.", new ArrayList<>());

            cocktailRepository.saveAll(List.of(mojito, bloodyMary, deathInTheAfternoon));
        };
    }
}


