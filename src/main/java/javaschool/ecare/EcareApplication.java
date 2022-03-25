package javaschool.ecare;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.ConnectionFactory;
import javaschool.ecare.loader.LoaderService;
import javaschool.ecare.loader.LoaderServiceImpl;
import javaschool.ecare.loader.Message;
import javaschool.ecare.repositories.TariffRepository;
import javaschool.ecare.services.api.TariffService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class EcareApplication {

    @Bean
    public ConnectionFactory connectionFactory() { return new ConnectionFactory(); }

    @Bean
    public ObjectMapper objectMapper() { return new ObjectMapper(); }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() { return new BCryptPasswordEncoder(); }

    public static void main(String[] args) {

        SpringApplication.run(EcareApplication.class, args);

    }



}
