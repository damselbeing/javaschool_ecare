package javaschool.ecare.services.impl;

import javaschool.ecare.dto.ClientDto;
import javaschool.ecare.entities.Client;
import javaschool.ecare.exceptions.ClientNotFoundException;
import javaschool.ecare.repositories.ClientRepository;
import javaschool.ecare.services.api.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ModelMapper mapper;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, ModelMapper mapper) {
        this.clientRepository = clientRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ClientDto> getClients() {
        List<Client> getAll = clientRepository.findAll();
        return getAll.stream()
                .map(client -> mapper.map(client, ClientDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void addNewClient(ClientDto dto) {
        clientRepository.save(mapper.map(dto, Client.class));
    }

    @Transactional
    @Override
    public ClientDto findClientByPassport(String passport) throws ClientNotFoundException {
        return clientRepository.findClientByPassport(passport)
                .map(client -> mapper.map(client, ClientDto.class))
                .orElseThrow(ClientNotFoundException::new);
    }

    @Transactional
    @Override
    public ClientDto findClientByContract(String contract) throws ClientNotFoundException {
        return clientRepository.findClientByContract(contract)
                .map(client -> mapper.map(client, ClientDto.class))
                .orElseThrow(ClientNotFoundException::new);
    }
}
