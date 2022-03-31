package javaschool.ecare.services.impl;

import javaschool.ecare.dto.ClientDto;
import javaschool.ecare.entities.Client;
import javaschool.ecare.entities.Contract;
import javaschool.ecare.exceptions.*;
import javaschool.ecare.repositories.ClientRepository;
import javaschool.ecare.repositories.ContractRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    ContractRepository contractRepository;
    @Mock
    ClientRepository clientRepository;
    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    private ModelMapper modelMapper;
    private ClientServiceImpl clientService;
    private Client client1;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        client1 = new Client(1L, "anna", "winter", LocalDate.of(2000, 10, 10), "111111", "moscow", "ana@gmail.com", "password");
        clientService = new ClientServiceImpl(clientRepository, modelMapper, contractRepository, bCryptPasswordEncoder);
    }

    @Test
    void shouldFindClientByIdClient() throws ClientNotFoundException {
        when(clientRepository.findClientByIdClient(1L))
                .thenReturn(Optional.ofNullable(client1));

        Client actual = modelMapper.map(clientService.findClientByIdClient(1L), Client.class);
        Client expected = client1;

        assertEquals(expected, actual);
    }

    @Test
    void shouldGetClients() {
        when(clientRepository.findAll()).thenReturn(new ArrayList<>());
        clientService.getClients();
        verify(clientRepository).findAll();
    }

    @Test
    void shouldRegisterNewClient() throws PasswordConfirmationFailedException, ClientAlreadyExistsException {
        client1.setPasswordConfirm("password");
        ClientDto dto = modelMapper.map(client1, ClientDto.class);

        assertFalse(clientRepository.findClientByEmail(client1.getEmail().toLowerCase()).isPresent());
        assertFalse(clientRepository.findClientByPassport(client1.getPassport().toUpperCase()).isPresent());

        when(bCryptPasswordEncoder.encode(client1.getPassword()))
                .thenReturn("changedPassword");

        clientService.registerNewClient(dto);

        Client client1Clone = new Client();
        client1Clone.setIdClient(client1.getIdClient());
        client1Clone.setBirthDate(client1.getBirthDate());
        client1Clone.setName(client1.getName());
        client1Clone.setLastName(client1.getLastName());
        client1Clone.setRole("USER");
        client1Clone.setPassport(client1.getPassport());
        client1Clone.setAddress(client1.getAddress());
        client1Clone.setEmail(client1.getEmail());
        client1Clone.setPassword(bCryptPasswordEncoder.encode(client1.getPassword()));
        client1Clone.setPasswordConfirm(client1.getPasswordConfirm());

        verify(clientRepository).save(client1Clone);

    }

    @Test
    void shouldNotRegisterNewClient() {

        ClientDto dto = modelMapper.map(client1, ClientDto.class);

        when(clientRepository.findClientByEmail(client1.getEmail()))
                .thenReturn(Optional.ofNullable(client1));

        assertThrows(ClientAlreadyExistsException.class,
                ()->{
                    clientService.registerNewClient(dto);
                });

    }

    @Test
    void shouldFindClientByContract() throws ClientNotFoundException, ContractNotFoundException {
        Contract contract1 = new Contract(1L, "49000000000", false, false);
        client1.setContract(contract1);

        when(clientRepository.findClientByContract(contract1))
                .thenReturn(Optional.ofNullable(client1));

        when(contractRepository.findContractByNumber("49000000000"))
                .thenReturn(Optional.ofNullable(contract1));

        Client actual = modelMapper.map(clientService.findClientByContract("49000000000"), Client.class);
        Client expected = client1;

        assertEquals(expected, actual);
    }

    @Test
    void shouldFindClientByEmail() throws ClientNotFoundException {
        when(clientRepository.findClientByEmail("ana@gmail.com"))
                .thenReturn(Optional.ofNullable(client1));

        Client actual = modelMapper.map(clientService.findClientByEmail("ana@gmail.com"), Client.class);
        Client expected = client1;

        assertEquals(expected, actual);
    }
}