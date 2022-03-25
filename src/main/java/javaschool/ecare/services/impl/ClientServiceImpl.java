package javaschool.ecare.services.impl;

import javaschool.ecare.dto.ClientDto;
import javaschool.ecare.entities.Client;
import javaschool.ecare.entities.Contract;
import javaschool.ecare.exceptions.ClientAlreadyExistsException;
import javaschool.ecare.exceptions.ClientNotFoundException;
import javaschool.ecare.exceptions.ContractNotFoundException;
import javaschool.ecare.exceptions.PasswordConfirmationFailedException;
import javaschool.ecare.repositories.ClientRepository;
import javaschool.ecare.repositories.ContractRepository;
import javaschool.ecare.services.api.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ContractRepository contractRepository;
    private final ModelMapper mapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public ClientServiceImpl(
            ClientRepository clientRepository,
            ModelMapper mapper,
            ContractRepository contractRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.clientRepository = clientRepository;
        this.mapper = mapper;
        this.contractRepository = contractRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional(readOnly = true)
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


    @Transactional
    @Override
    public void registerNewClient(ClientDto dto) throws ClientAlreadyExistsException, PasswordConfirmationFailedException {
        Client client = mapper.map(dto, Client.class);
        if(
                clientRepository.findClientByEmail(client.getEmail().toLowerCase()).isPresent() ||
                        clientRepository.findClientByPassport(client.getPassport().toUpperCase()).isPresent()
        ) {
            throw new ClientAlreadyExistsException();
        };

        if (!dto.getPassword().equals(dto.getPasswordConfirm())){
           throw new PasswordConfirmationFailedException();
        }

        client.setRole("USER");
        client.setPassport(client.getPassport().toUpperCase());
        client.setEmail(client.getEmail().toLowerCase());
        client.setPassword(bCryptPasswordEncoder.encode(client.getPassword()));
        clientRepository.save(client);

    }


    @Transactional
    @Override
    public ClientDto findClientByContract(String number) throws ClientNotFoundException, ContractNotFoundException {
        Contract contract = contractRepository.findContractByNumber(number).orElseThrow(ContractNotFoundException::new);
        return clientRepository.findClientByContract(contract)
                .map(client -> mapper.map(client, ClientDto.class))
                .orElseThrow(ClientNotFoundException::new);
    }

    @Transactional(readOnly = true)
    @Override
    public ClientDto findClientByEmail(String email) throws ClientNotFoundException {
        return clientRepository.findClientByEmail(email)
                .map(client -> mapper.map(client, ClientDto.class))
                .orElseThrow(ClientNotFoundException::new);
    }
}
