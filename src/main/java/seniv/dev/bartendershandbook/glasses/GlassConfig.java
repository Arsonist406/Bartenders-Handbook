package seniv.dev.bartendershandbook.glasses;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;

@Configuration
public class GlassConfig {

    @Bean
    @Order(1)
    CommandLineRunner glassesCommandLineRunner(GlassRepository glassRepository) {
        return (args) -> {
            var glassesList = List.of(
                    new Glass(null, "Default Glass", "Generic glass, unspecified use"),
                    new Glass(null, "Highball", "A tall glass used for mixed drinks, typically filled with ice and soda or tonic water"),
                    new Glass(null, "Martini", "A stemmed glass used for serving martinis, typically without ice and with a broad rim"),
                    new Glass(null, "Shot", "A small glass used for serving spirits or liqueurs in small quantities, often consumed in one go"),
                    new Glass(null, "Hurricane", "A large, curved glass designed for tropical cocktails, typically with a wide bowl to hold fruity, mixed drinks")
            );
            glassRepository.saveAll(glassesList);
        };
    }
}
