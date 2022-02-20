package javaschool.ecare.services;

import javaschool.ecare.converters.ClientConverter;
import javaschool.ecare.dto.ClientDto;
import javaschool.ecare.entities.Client;
import javaschool.ecare.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired ClientRepository clientRepository;
    @Autowired ClientConverter converter;

//    @Autowired
//    public ClientService(ClientRepository clientRepository) {
//        this.clientRepository = clientRepository;
//    }

    public List<ClientDto> getClients() {

        List<Client> getAll = clientRepository.findAll();
        return converter.entityToDto(getAll);
    }

}
