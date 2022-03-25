package javaschool.ecare.services.impl;

import javaschool.ecare.dto.ContractDto;
import javaschool.ecare.entities.Client;
import javaschool.ecare.entities.Contract;
import javaschool.ecare.entities.Option;
import javaschool.ecare.entities.Tariff;
import javaschool.ecare.exceptions.*;
import javaschool.ecare.repositories.ClientRepository;
import javaschool.ecare.repositories.ContractRepository;
import javaschool.ecare.repositories.OptionRepository;
import javaschool.ecare.repositories.TariffRepository;
import javaschool.ecare.services.api.ContractService;
import javaschool.ecare.loader.LoaderService;
import javaschool.ecare.services.api.TariffService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Service
public class ContractServiceImpl implements ContractService {


    private static final String BASE_NUMBER = "4909001";

    private final ContractRepository contractRepository;
    private final OptionRepository optionRepository;
    private final ClientRepository clientRepository;
    private final TariffRepository tariffRepository;
    private final TariffService tariffService;
    private final LoaderService loaderService;
    private final ModelMapper mapper;

    @Autowired
    public ContractServiceImpl(ContractRepository contractRepository,
                               OptionRepository optionRepository,
                               TariffRepository tariffRepository,
                               TariffServiceImpl tariffService,
                               ClientRepository clientRepository,
                               LoaderService loaderService,
                               ModelMapper mapper) {
        this.contractRepository = contractRepository;
        this.optionRepository = optionRepository;
        this.tariffRepository = tariffRepository;
        this.clientRepository = clientRepository;
        this.tariffService = tariffService;
        this.loaderService = loaderService;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ContractDto> getContracts() {
        return contractRepository.findAll().stream()
                .map(contract -> mapper.map(contract, ContractDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public ContractDto findContractByIdContract(Long idContract) throws ContractNotFoundException {
        return contractRepository.findContractByIdContract(idContract)
                .map(contract -> mapper.map(contract, ContractDto.class))
                .orElseThrow(ContractNotFoundException::new);
    }

    @Transactional
    @Override
    public void blockByAdmin(Long idContract) throws ContractNotFoundException {
        Contract contract = contractRepository.findContractByIdContract(idContract).orElseThrow(ContractNotFoundException::new);
        contract.setBlockedByAdmin(true);
    }

    @Transactional
    @Override
    public void blockByClient(Long idContract) throws ContractNotFoundException {
        Contract contract = contractRepository.findContractByIdContract(idContract).orElseThrow(ContractNotFoundException::new);
        contract.setBlockedByClient(true);
    }

    @Transactional
    @Override
    public void unblockByAdmin(Long idContract) throws ContractNotFoundException {
        Contract contract = contractRepository.findContractByIdContract(idContract).orElseThrow(ContractNotFoundException::new);
        contract.setBlockedByAdmin(false);
        contract.setBlockedByClient(false);
    }

    @Transactional
    @Override
    public void unblockByClient(Long idContract) throws ContractNotFoundException {
        Contract contract = contractRepository.findContractByIdContract(idContract).orElseThrow(ContractNotFoundException::new);

        if(contract.isBlockedByAdmin() == false) {
            contract.setBlockedByClient(false);
        }

    }

    @Transactional
    @Override
    public void updateContractTariff(Long idContract, String idTariff) throws ContractNotFoundException, TariffNotFoundException, TariffAlreadyExistsException, IOException, TimeoutException {
        Contract contract = contractRepository.findContractByIdContract(idContract).orElseThrow(ContractNotFoundException::new);

        if(idTariff != null && contract.isBlockedByAdmin() == false && contract.isBlockedByClient() == false) {
            Tariff tariff = tariffRepository.findTariffByIdTariff(Long.parseLong(idTariff)).orElseThrow(TariffNotFoundException::new);

            if(tariff.isArchived() == false) {
                contract.setTariff(tariff);
                contract.getContractOptions().forEach(option -> option.getContracts().remove(contract));
                loaderService.sendMessage();

            }
        }

    }

    @Transactional
    @Override
    public Set<String> getGeneratedNumbers() {
        Set<String> numbers = new HashSet<>();

        for(int i = 0; i < 10; i++) {
            String number = generateNumber();

            while (contractRepository.existsContractByNumber(number)) {
                number = generateNumber();
            }
            numbers.add(number);
        }

        return numbers;
    }

    @Override
    public String generateNumber() {
        Random random = new Random();
        String result;
        int suffix = random.nextInt(9999) + 1;
        if (suffix < 10)
            result = BASE_NUMBER + "000" + suffix;
        else if (suffix < 100)
            result = BASE_NUMBER + "00" + suffix;
        else if (suffix < 1000)
            result = BASE_NUMBER + "0" + suffix;
        else
            result = BASE_NUMBER + suffix;
        return result;
    }

    @Transactional
    @Override
    public void addNewContract(ContractDto dto, Long idClient) throws ClientNotFoundException, ContractNotFoundException {
        contractRepository.save(mapper.map(dto, Contract.class));
        Client client = clientRepository.findClientByIdClient(idClient).orElseThrow(ClientNotFoundException::new);
        Contract contract = contractRepository.findContractByNumber(dto.getNumber()).orElseThrow(ContractNotFoundException::new);
        client.setContract(contract);
    }

    @Transactional
    @Override
    public void updateContractOptions(Long idContract, String[] options) throws ContractNotFoundException, OptionNotFoundException, NotValidOptionsException {
        Contract contract = contractRepository.findContractByIdContract(idContract).orElseThrow(ContractNotFoundException::new);

        if(contract.isBlockedByClient() == false && contract.isBlockedByAdmin() == false) {
            Set<Option> optionsUpdated = tariffService.prepareTariffOptionsForUpdate(options);
            contract.getContractOptions().forEach(option -> option.getContracts().remove(contract));
            contract.setContractOptions(optionsUpdated);
            optionsUpdated.forEach(option -> option.getContracts().add(contract));
        }



    }

}
