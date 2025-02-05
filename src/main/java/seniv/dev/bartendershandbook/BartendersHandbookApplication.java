package seniv.dev.bartendershandbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "seniv.dev.bartendershandbook.configs",
        "seniv.dev.bartendershandbook.controllers",
        "seniv.dev.bartendershandbook.entities",
        "seniv.dev.bartendershandbook.services",
        "seniv.dev.bartendershandbook.validation"
})
public class BartendersHandbookApplication {

    public static void main(String[] args) {
        SpringApplication.run(BartendersHandbookApplication.class, args);
    }
}
