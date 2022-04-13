package javaschool.ecare.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import javaschool.ecare.dto.TariffDto;
import javaschool.ecare.exceptions.TariffNotFoundException;
import javaschool.ecare.services.api.TariffService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.context.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeoutException;

@Log4j2
@Service
public class LoaderServiceImpl implements LoaderService {

        private final ConnectionFactory connectionFactory;
        private final TariffService tariffService;
        private final ObjectMapper objectMapper;

        @Autowired
        public LoaderServiceImpl(
                ConnectionFactory connectionFactory,
                TariffService tariffService,
                ObjectMapper objectMapper) {
                this.connectionFactory = connectionFactory;
                this.tariffService = tariffService;
                this.objectMapper = objectMapper;
        }

        @Override
        @EventListener(ApplicationReadyEvent.class)
        public void sendMessage()
                throws IOException, TimeoutException, TariffNotFoundException {
                TariffDto popTariff = findPopTariff();
                Message message = createMessage(popTariff);
                String json = objectMapper.writeValueAsString(message);
                Connection connection = connectionFactory.newConnection();                                                                                                                                                                              //NOSONAR not used in secure contexts
                Channel channel = connection.createChannel();                                                                                                                                                                                                                   //NOSONAR not used in secure contexts
                channel.queueDeclare(
                        "pop_tariff", false, false, false, null);
                channel.basicPublish(
                        "", "pop_tariff", false, null, json.getBytes(StandardCharsets.UTF_8));
                log.info("message has been sent to Epromo: " + message.getTariffName());

        }

        private Message createMessage(TariffDto popTariff) {
                Message message = new Message();
                message.setTariffName(popTariff.getName());
                List<String> tariffOptions = new ArrayList<>();
                popTariff.getOptions().forEach(
                        option -> tariffOptions.add(option.getName())
                );
                message.setTariffOptions(tariffOptions);
                return message;
        }

        private TariffDto findPopTariff() throws TariffNotFoundException {

                List<TariffDto> tariffs = tariffService.getTariffs();
                Map<Long, Integer> map = new HashMap<>();
                tariffs.forEach(tariff -> map.put(tariff.getIdTariff(), tariff.getContracts().size()));


                int maxValueInMap = (Collections.max(map.values()));
                Long tariffId = null;

                for (Map.Entry<Long, Integer> entry :
                        map.entrySet()) {

                        if (entry.getValue() == maxValueInMap) {
                               tariffId = entry.getKey();
                        }
                }

                return tariffService.findTariffByIdTariff(tariffId);
        }


}
