package javaschool.ecare.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import javaschool.ecare.entities.Tariff;
import javaschool.ecare.exceptions.TariffAlreadyExistsException;
import javaschool.ecare.repositories.TariffRepository;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.*;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeoutException;

@Service
public class LoaderServiceImpl implements LoaderService {

        private final ConnectionFactory connectionFactory;
        private final TariffRepository tariffRepository;
        private final ObjectMapper objectMapper;

        @Autowired
        public LoaderServiceImpl(
                ConnectionFactory connectionFactory,
                TariffRepository tariffRepository,
                ObjectMapper objectMapper) {
                this.connectionFactory = connectionFactory;
                this.tariffRepository = tariffRepository;
                this.objectMapper = objectMapper;
        }

        @Override
        @EventListener(ApplicationReadyEvent.class)
        public void sendMessage() throws IOException, TimeoutException, TariffAlreadyExistsException {
                Tariff popTariff = findPopTariff();
                Message message = createMessage(popTariff);
                String json = objectMapper.writeValueAsString(message);
                try(Connection connection = connectionFactory.newConnection()) {
                        Channel channel = connection.createChannel();
                        channel.queueDeclare("pop_tariff", false, false, false, null);
                        channel.basicPublish("", "pop_tariff", false, null, json.getBytes(StandardCharsets.UTF_8));
                        System.out.println("message has been sent");
                }
        }

        private Message createMessage(Tariff popTariff) {
                Message message = new Message();
                message.setTariffName(popTariff.getName());
                List<String> tariffOptions = new ArrayList<>();
                popTariff.getOptions().forEach(
                        option -> tariffOptions.add(option.getName())
                );
                message.setTariffOptions(tariffOptions);
                return message;
        }

        private Tariff findPopTariff() throws TariffAlreadyExistsException {

                List<Tariff> tariffs = tariffRepository.findAll();
                Map<Long, Integer> map = new HashMap<>();
                tariffs.forEach(tariff -> map.put(tariff.getIdTariff(), tariff.getContracts().size()));
                System.out.println(map);

                int maxValueInMap = (Collections.max(map.values()));
                Long tariffId = null;

                for (Map.Entry<Long, Integer> entry :
                        map.entrySet()) {

                        if (entry.getValue() == maxValueInMap) {
                               tariffId = entry.getKey();
                        }
                }

                Tariff popTariff = tariffRepository.findTariffByIdTariff(tariffId).orElseThrow(TariffAlreadyExistsException::new);

                return popTariff;
        }


}
