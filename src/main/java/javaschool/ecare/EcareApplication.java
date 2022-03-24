package javaschool.ecare;

import com.rabbitmq.client.ConnectionFactory;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class EcareApplication {

    private ConnectionFactory factory = null;

    @Bean
    public ConnectionFactory connectionFactory() {
        if (this.factory == null) {
            this.factory = new ConnectionFactory();
        }
        return this.factory;
    }

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
