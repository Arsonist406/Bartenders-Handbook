package seniv.dev.bartendershandbook.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import seniv.dev.bartendershandbook.module.entity.glass.Glass;
import seniv.dev.bartendershandbook.repository.GlassRepository;

import java.util.List;

@Configuration
public class GlassConfig {

    @Bean
    @Order(1)
    CommandLineRunner glassesCommandLineRunner(GlassRepository glassRepository) {
        return (args) -> {
            var glassesList = List.of(
                    new Glass("Default Glass", "Generic glass, unspecified use"),
                    new Glass("Highball", "A tall glass used for mixed drinks, typically filled with ice and soda or tonic water"),
                    new Glass("Martini", "A stemmed glass used for serving martinis, typically without ice and with a broad rim"),
                    new Glass("Shot", "A small glass used for serving spirits or liqueurs in small quantities, often consumed in one go"),
                    new Glass("Hurricane", "A large, curved glass designed for tropical cocktails, typically with a wide bowl to hold fruity, mixed drinks")
            );
            glassRepository.saveAll(glassesList);
        };
    }
}
