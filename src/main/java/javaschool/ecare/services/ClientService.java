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

    private final ClientRepository clientRepository;
    private final ClientConverter converter;

    @Autowired
    public ClientService(ClientRepository clientRepository, ClientConverter converter) {
        this.clientRepository = clientRepository;
        this.converter = converter;
    }

    public List<ClientDto> getClients() {
        List<Client> getAll = clientRepository.findAll();
        return converter.entityToDto(getAll);
    }

    public void addNewClient(ClientDto dto) {
        clientRepository.save(converter.dtoToEntity(dto));
    }


}
