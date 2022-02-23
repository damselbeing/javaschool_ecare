package javaschool.ecare.services.impl;

import javaschool.ecare.converters.ClientConverter;
import javaschool.ecare.dto.ClientDto;
import javaschool.ecare.entities.Client;
import javaschool.ecare.repositories.ClientRepository;
import javaschool.ecare.services.api.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientConverter converter;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, ClientConverter converter) {
        this.clientRepository = clientRepository;
        this.converter = converter;
    }

    @Transactional
    @Override
    public List<ClientDto> getClients() {
        List<Client> getAll = clientRepository.findAll();
        return converter.entityToDto(getAll);
    }

    @Transactional
    @Override
    public void addNewClient(ClientDto dto) {
        clientRepository.save(converter.dtoToEntity(dto));
    }


}
