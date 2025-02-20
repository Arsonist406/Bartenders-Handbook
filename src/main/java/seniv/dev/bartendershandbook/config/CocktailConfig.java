package seniv.dev.bartendershandbook.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import seniv.dev.bartendershandbook.module.entity.Cocktail;
import seniv.dev.bartendershandbook.module.entity.Glass;
import seniv.dev.bartendershandbook.repository.CocktailRepository;
import seniv.dev.bartendershandbook.repository.GlassRepository;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
public class CocktailConfig {

    @Bean
    @Order(3)
    CommandLineRunner cocktailCommandLineRunner(
            CocktailRepository cocktailRepository,
            GlassRepository glassRepository
    ) {
        return (args) -> {
            Set<Glass> mojitoGlasses = new HashSet<>();
            mojitoGlasses.add(glassRepository.findByName("Default Glass")
                    .orElseThrow(() -> new IllegalStateException("Glass 'Default Glass' not found")));
            mojitoGlasses.add(glassRepository.findByName("Highball")
                    .orElseThrow(() -> new IllegalStateException("Glass 'Highball' not found")));
            mojitoGlasses.add(glassRepository.findByName("Hurricane")
                    .orElseThrow(() -> new IllegalStateException("Glass 'Hurricane' not found")));


            Set<Glass> bloodyMaryGlasses = new HashSet<>();
            bloodyMaryGlasses.add(glassRepository.findByName("Default Glass")
                    .orElseThrow(() -> new IllegalStateException("Glass 'Default Glass' not found")));
            bloodyMaryGlasses.add(glassRepository.findByName("Highball")
                    .orElseThrow(() -> new IllegalStateException("Glass 'Highball' not found")));


            Set<Glass> deathGlasses = new HashSet<>();
            deathGlasses.add(glassRepository.findByName("Default Glass")
                    .orElseThrow(() -> new IllegalStateException("Glass 'Default Glass' not found")));
            deathGlasses.add(glassRepository.findByName("Martini")
                    .orElseThrow(() -> new IllegalStateException("Glass 'Martini' not found")));

            Cocktail mojito = new Cocktail(
                    "Mojito",
                    300,
                    BigDecimal.valueOf(14.0),
                    mojitoGlasses,
                    "Refreshing rum-based cocktail",
                    "Muddle mint leaves, lime, and sugar in a glass. Add rum and top with soda water. Serve with ice and garnish with mint.",
                    new HashSet<>()
            );
            Cocktail bloodyMary = new Cocktail(
                    "Bloody Mary",
                    400,
                    BigDecimal.valueOf(18.0),
                    bloodyMaryGlasses,
                    "Spicy and savory vodka cocktail",
                    "Mix vodka, tomato juice, lemon juice, Worcestershire sauce, Tabasco, salt, and pepper in a shaker. Pour into a glass filled with ice and garnish with celery.",
                    new HashSet<>()
            );
            Cocktail deathInTheAfternoon = new Cocktail(
                    "Death in the Afternoon",
                    200,
                    BigDecimal.valueOf(12.07),
                    deathGlasses,
                    "A refreshing champagne cocktail",
                    "Pour tequila into a glass and top with champagne. Stir gently and garnish with a lime wedge.",
                    new HashSet<>()
            );

            cocktailRepository.saveAll(List.of(mojito, bloodyMary, deathInTheAfternoon));
        };
    }
}


