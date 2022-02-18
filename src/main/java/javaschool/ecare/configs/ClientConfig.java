package javaschool.ecare.configs;

import javaschool.ecare.entities.Client;
import javaschool.ecare.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class ClientConfig {

    @Bean
    CommandLineRunner commandLineRunner(ClientRepository repository) {
        return args -> {
            Client anna = new Client(
                    "anna",
                    "winter",
                    LocalDate.parse("2001-01-01"),
                    "0000 000000",
                    "moscow",
                    "anna@mail",
                    "12345"
            );

            Client kate = new Client(
                    "kate",
                    "summer",
                    LocalDate.parse("2000-12-12"),
                    "1111 111111",
                    "perm",
                    "kate@mail",
                    "67890"
            );

            repository.saveAll(
                    List.of(anna, kate)
            );
        };
    }

}

