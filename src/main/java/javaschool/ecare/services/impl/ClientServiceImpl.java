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
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
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

    /**
     * {@inheritdoc}
     */
    @Transactional(readOnly = true)
    @Override
    public ClientDto findClientByIdClient(Long id) throws ClientNotFoundException {
        return clientRepository.findClientByIdClient(id)
                .map(client -> mapper.map(client, ClientDto.class))
                .orElseThrow(ClientNotFoundException::new);
    }

    /**
     * {@inheritdoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<ClientDto> getClients() {
        return clientRepository.findAll().stream()
                .map(client -> mapper.map(client, ClientDto.class))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritdoc}
     */
    @Transactional
    @Override
    public void registerNewClient(ClientDto dto) throws ClientAlreadyExistsException, PasswordConfirmationFailedException {
        Client client = mapper.map(dto, Client.class);
        if(
                clientRepository.findClientByEmail(client.getEmail().toLowerCase()).isPresent() ||
                        clientRepository.findClientByPassport(client.getPassport().toUpperCase()).isPresent()
        ) {
            log.error("Client can't be saved because there is an account with this email: " + client.getEmail());
            log.error("or Client can't be saved because there is an account with this passport: " + client.getPassport());
            throw new ClientAlreadyExistsException();
        };

        if (!dto.getPassword().equals(dto.getPasswordConfirm())){
            log.error("Client can't be saved because the password confirmation failed");
            throw new PasswordConfirmationFailedException();
        }

        client.setRole("USER");
        client.setPassport(client.getPassport().toUpperCase());
        client.setEmail(client.getEmail().toLowerCase());
        client.setPassword(bCryptPasswordEncoder.encode(client.getPassword()));
        clientRepository.save(client);
    }

    /**
     * {@inheritdoc}
     */
    @Transactional
    @Override
    public ClientDto findClientByContract(String number) throws ClientNotFoundException, ContractNotFoundException {
        Contract contract = contractRepository.findContractByNumber(number).orElseThrow(ContractNotFoundException::new);
        return clientRepository.findClientByContract(contract)
                .map(client -> mapper.map(client, ClientDto.class))
                .orElseThrow(ClientNotFoundException::new);
    }

    /**
     * {@inheritdoc}
     */
    @Transactional(readOnly = true)
    @Override
    public ClientDto findClientByEmail(String email) throws ClientNotFoundException {
        return clientRepository.findClientByEmail(email)
                .map(client -> mapper.map(client, ClientDto.class))
                .orElseThrow(ClientNotFoundException::new);
    }

}
