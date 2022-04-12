package javaschool.ecare.services.impl;

import javaschool.ecare.dto.ContractDto;
import javaschool.ecare.entities.Client;
import javaschool.ecare.entities.Contract;
import javaschool.ecare.entities.Option;
import javaschool.ecare.entities.Tariff;
import javaschool.ecare.exceptions.*;
import javaschool.ecare.loader.LoaderService;
import javaschool.ecare.loader.LoaderServiceImpl;
import javaschool.ecare.repositories.ClientRepository;
import javaschool.ecare.repositories.ContractRepository;
import javaschool.ecare.repositories.OptionRepository;
import javaschool.ecare.repositories.TariffRepository;
import javaschool.ecare.services.api.TariffService;
import org.checkerframework.checker.nullness.Opt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContractServiceTest {

    @Mock
    OptionRepository optionRepository;
    @Mock
    TariffRepository tariffRepository;
    @Mock
    ContractRepository contractRepository;
    @Mock
    ClientRepository clientRepository;
    @Mock
    TariffServiceImpl tariffService;
    @Mock
    LoaderServiceImpl loaderService;

    private ModelMapper modelMapper;
    private ContractServiceImpl contractService;
    private Contract contract1;

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException {
        modelMapper = new ModelMapper();
        contractService = new ContractServiceImpl(
                contractRepository,
                optionRepository,
                tariffRepository,
                tariffService,
                clientRepository,
                loaderService,
                modelMapper);
        contract1 = new Contract(1L, "49000000000", false, false);

    }

    @Test
    void shouldGetContracts() {
        when(contractRepository.findAll()).thenReturn(new ArrayList<>());
        contractService.getContracts();
        verify(contractRepository).findAll();
    }

    @Test
    void shouldFindContractByIdContract() throws ContractNotFoundException {
        when(contractRepository.findContractByIdContract(1L))
                .thenReturn(Optional.ofNullable(contract1));

        Contract actual = modelMapper.map(contractService.findContractByIdContract(1L), Contract.class);
        Contract expected = contract1;

        assertEquals(expected, actual);
    }

    @Test
    void shouldBlockByAdmin() throws ContractNotFoundException {
        when(contractRepository.findContractByIdContract(1L))
                .thenReturn(Optional.ofNullable(contract1));
        contractService.blockByAdmin(1L);
        assertEquals(true, contract1.isBlockedByAdmin());
    }

    @Test
    void shouldBlockByClient() throws ContractNotFoundException {
        when(contractRepository.findContractByIdContract(1L))
                .thenReturn(Optional.ofNullable(contract1));
        contractService.blockByClient(1L);
        assertEquals(true, contract1.isBlockedByClient());
    }

    @Test
    void shouldUnblockByAdmin() throws ContractNotFoundException {
        when(contractRepository.findContractByIdContract(1L))
                .thenReturn(Optional.ofNullable(contract1));
        contractService.unblockByAdmin(1L);
        assertEquals(false, contract1.isBlockedByAdmin());
    }

    @Test
    void shouldUnblockByClient() throws ContractNotFoundException {
        when(contractRepository.findContractByIdContract(1L))
                .thenReturn(Optional.ofNullable(contract1));
        contractService.unblockByClient(1L);
        assertEquals(false, contract1.isBlockedByClient());
    }

    @Test
    void shouldUpdateContractTariff() throws TariffNotFoundException, IOException, ContractNotFoundException, TimeoutException {
        Tariff tariff1New = new Tariff(1L, "Tariff1", 10.00, false, false);
        Tariff tariff2Old = new Tariff(2L, "Tariff2", 30.00, false, false);

        Option option3 = new Option(3L, "Option3", 30.00, 40.00);
        Option option4 = new Option(4L, "Option4", 50.00, 60.00);

        contract1.setTariff(tariff2Old);

        Set<Contract> tariff2OldContracts = new HashSet<>();
        tariff2OldContracts.add(contract1);
        tariff2Old.setContracts(tariff2OldContracts);

        Set<Contract> contractOpt3 = new HashSet<>();
        contractOpt3.add(contract1);
        option3.setContracts(contractOpt3);

        Set<Contract> contractOpt4 = new HashSet<>();
        contractOpt4.add(contract1);
        option4.setContracts(contractOpt4);

        Set<Option> contractOptions = new HashSet<>();
        contractOptions.add(option3);
        contractOptions.add(option4);
        contract1.setContractOptions(contractOptions);

        when(contractRepository.findContractByIdContract(1L))
                .thenReturn(Optional.ofNullable(contract1));

        when(tariffRepository.findTariffByIdTariff(1L))
                .thenReturn(Optional.ofNullable(tariff1New));

        contractService.updateContractTariff(1L, "1");

        assertEquals(tariff1New, contract1.getTariff());
    }

//    @Test
//    void shouldGetGeneratedNumbers() {
//    }
//
//    @Test
//    void shouldGenerateNumber() {
//    }

    @Test
    void shouldAddNewContract() throws ClientNotFoundException, ContractNotFoundException {
        Client client1 = new Client(1L, "anna", "winter", LocalDate.of(2000, 10, 10), "111111", "moscow", "ana@gmail.com", "12qwer34FF");

        ContractDto dto = modelMapper.map(contract1, ContractDto.class);

        when(clientRepository.findClientByIdClient(1L))
                .thenReturn(Optional.ofNullable(client1));

        when(contractRepository.findContractByNumber("49000000000"))
                .thenReturn(Optional.ofNullable(contract1));

        contractService.addNewContract(dto, 1L);

        assertEquals(contract1, client1.getContract());

    }

    @Test
    void shouldUpdateContractOptions() throws OptionNotFoundException, ContractNotFoundException, NotValidOptionsException {
        Option option3 = new Option(3L, "Option3", 30.00, 40.00);
        Option option4 = new Option(4L, "Option4", 50.00, 60.00);
        Option option5 = new Option(5L, "Option5", 70.00, 90.00);

        Set<Contract> contractOpt3 = new HashSet<>();
        contractOpt3.add(contract1);
        option3.setContracts(contractOpt3);

        Set<Contract> contractOpt4 = new HashSet<>();
        contractOpt4.add(contract1);
        option4.setContracts(contractOpt4);

        Set<Contract> contractOpt5 = new HashSet<>();
        contractOpt5.add(contract1);
        option5.setContracts(contractOpt5);

        Set<Option> contractOptions = new HashSet<>();
        contractOptions.add(option3);
        contractOptions.add(option4);
        contract1.setContractOptions(contractOptions);

        String[] options = {"5"};
        Set<Option> optionsUpdated = new HashSet<>();
        optionsUpdated.add(option5);

        when(contractRepository.findContractByIdContract(1L))
                .thenReturn(Optional.ofNullable(contract1));

        when(tariffService.prepareTariffOptionsForUpdate(options))
                .thenReturn(optionsUpdated);

        contractService.updateContractOptions(1L, options);

        assertEquals(optionsUpdated, contract1.getContractOptions());

    }
}