package javaschool.ecare.services.impl;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import javaschool.ecare.entities.Tariff;
import javaschool.ecare.exceptions.TariffAlreadyExistsException;
import javaschool.ecare.repositories.TariffRepository;
import javaschool.ecare.services.api.ContractService;
import javaschool.ecare.services.api.LoaderService;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeoutException;

@Service
public class LoaderServiceImpl implements LoaderService {

        private final ConnectionFactory connectionFactory;
        private final TariffRepository tariffRepository;

        @Autowired
        public LoaderServiceImpl(ConnectionFactory connectionFactory, TariffRepository tariffRepository) {
                this.connectionFactory = connectionFactory;
                this.tariffRepository = tariffRepository;
        }

        @Override
        public void loadMessage() throws IOException, TimeoutException, TariffAlreadyExistsException {
                String popTariff = findPopTariff();
                try(Connection connection = connectionFactory.newConnection()) {
                        Channel channel = connection.createChannel();
                        channel.queueDeclare("pop_tariff", false, false, false, null);
                        channel.basicPublish("", "pop_tariff", false, null, popTariff.getBytes());
                        System.out.println("message has been sent");
                }
        }

        @Override
        public String findPopTariff() throws TariffAlreadyExistsException {

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
                String tariffName = popTariff.getName();

                return tariffName;
        }



}
