package seniv.dev.bartendershandbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import static tec.units.ri.quantity.Quantities.getQuantity;

@SpringBootApplication
@ComponentScan(basePackages = {
        "seniv.dev.bartendershandbook.cocktails",
        "seniv.dev.bartendershandbook.ingredients",
        "seniv.dev.bartendershandbook.glasses",
        "seniv.dev.bartendershandbook.cocktails_ingredients"
})
public class BartendersHandbookApplication {

    public static void main(String[] args) {
        SpringApplication.run(BartendersHandbookApplication.class, args);
    }
}
