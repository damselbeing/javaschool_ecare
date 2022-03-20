package javaschool.ecare.services.impl;

import javaschool.ecare.dto.ClientDto;
import javaschool.ecare.entities.Client;
import javaschool.ecare.entities.Contract;
import javaschool.ecare.exceptions.ClientNotFoundException;
import javaschool.ecare.exceptions.ContractNotFoundException;
import javaschool.ecare.repositories.ClientRepository;
import javaschool.ecare.repositories.ContractRepository;
import javaschool.ecare.services.api.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ContractRepository contractRepository;
    private final ModelMapper mapper;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, ModelMapper mapper, ContractRepository contractRepository) {
        this.clientRepository = clientRepository;
        this.mapper = mapper;
        this.contractRepository = contractRepository;
    }

    @Transactional
    @Override
    public ClientDto findClientByIdClient(Long id) throws ClientNotFoundException {
        return clientRepository.findClientByIdClient(id)
                .map(client -> mapper.map(client, ClientDto.class))
                .orElseThrow(ClientNotFoundException::new);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ClientDto> getClients() {
        return clientRepository.findAll().stream()
                .map(client -> mapper.map(client, ClientDto.class))
                .collect(Collectors.toList());
    }

//    @Transactional
//    @Override
//    public void addNewClient(ClientDto dto) {
//        clientRepository.save(mapper.map(dto, Client.class));
//    }

    @Transactional
    @Override
    public boolean saveClient(ClientDto dto) {
        Client client = mapper.map(dto, Client.class);
        if(clientRepository.findClientByEmail(client.getEmail()).isPresent()) {
            return false;
        };

//        client.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        clientRepository.save(client);
        return true;
    }


    @Transactional
    @Override
    public ClientDto findClientByContract(String number) throws ClientNotFoundException, ContractNotFoundException {
        Contract contract = contractRepository.findContractByNumber(number).orElseThrow(ContractNotFoundException::new);
        return clientRepository.findClientByContract(contract)
                .map(client -> mapper.map(client, ClientDto.class))
                .orElseThrow(ClientNotFoundException::new);
    }
}
