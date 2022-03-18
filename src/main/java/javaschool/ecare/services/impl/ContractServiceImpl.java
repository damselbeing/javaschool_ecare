package javaschool.ecare.services.impl;

import javaschool.ecare.dto.ContractDto;
import javaschool.ecare.entities.Contract;
import javaschool.ecare.entities.Option;
import javaschool.ecare.entities.Tariff;
import javaschool.ecare.exceptions.ClientNotFoundException;
import javaschool.ecare.exceptions.NotValidOptionsException;
import javaschool.ecare.exceptions.OptionNotFoundException;
import javaschool.ecare.exceptions.TariffNotFoundException;
import javaschool.ecare.repositories.ContractRepository;
import javaschool.ecare.repositories.OptionRepository;
import javaschool.ecare.repositories.TariffRepository;
import javaschool.ecare.services.api.ContractService;
import javaschool.ecare.services.api.TariffService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ContractServiceImpl implements ContractService {


    private static final String BASE_NUMBER = "8909001";

    private final ContractRepository contractRepository;
    private final OptionRepository optionRepository;
    private final TariffRepository tariffRepository;
    private final TariffService tariffService;
    private final ModelMapper mapper;

    @Autowired
    public ContractServiceImpl(ContractRepository contractRepository,
                               OptionRepository optionRepository,
                               TariffRepository tariffRepository,
                               TariffServiceImpl tariffService,
                               ModelMapper mapper) {
        this.contractRepository = contractRepository;
        this.optionRepository = optionRepository;
        this.tariffRepository = tariffRepository;
        this.tariffService = tariffService;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ContractDto> getContracts() {
        return contractRepository.findAll().stream()
                .map(contract -> mapper.map(contract, ContractDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ContractDto findContractByIdContract(Long id) throws ClientNotFoundException {
        return contractRepository.findContractByIdContract(id)
                .map(contract -> mapper.map(contract, ContractDto.class))
                .orElseThrow(ClientNotFoundException::new);
    }

    @Transactional
    @Override
    public void blockByAdmin(Long id) throws ClientNotFoundException {
        Contract contract = contractRepository.findContractByIdContract(id).orElseThrow(ClientNotFoundException::new);
        contract.setBlockedByAdmin(true);
    }

    @Transactional
    @Override
    public void blockByClient(Long id) throws ClientNotFoundException {
        Contract contract = contractRepository.findContractByIdContract(id).orElseThrow(ClientNotFoundException::new);
        contract.setBlockedByClient(true);
    }

    @Transactional
    @Override
    public void unblockByAdmin(Long id) throws ClientNotFoundException {
        Contract contract = contractRepository.findContractByIdContract(id).orElseThrow(ClientNotFoundException::new);
        contract.setBlockedByAdmin(false);
        contract.setBlockedByClient(false);
    }

    @Transactional
    @Override
    public void unblockByClient(Long id) throws ClientNotFoundException {
        Contract contract = contractRepository.findContractByIdContract(id).orElseThrow(ClientNotFoundException::new);
        contract.setBlockedByClient(false);
    }

    @Transactional
    @Override
    public void updateTariff(Long idContract, String idTariff) throws ClientNotFoundException, TariffNotFoundException {
        Contract contract = contractRepository.findContractByIdContract(idContract).orElseThrow(ClientNotFoundException::new);
        Tariff tariff = tariffRepository.findTariffByIdTariff(Long.parseLong(idTariff)).orElseThrow(TariffNotFoundException::new);
        contract.setTariff(tariff);
    }

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

    private String generateNumber() {
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
    public void updateContract(Long id, String[] options) throws ClientNotFoundException, OptionNotFoundException, NotValidOptionsException {
        Contract contract = contractRepository.findContractByIdContract(id).orElseThrow(ClientNotFoundException::new);
        Set<Option> optionsUpdated = tariffService.changeTariffOptions(options);
        contract.setContractOptions(optionsUpdated);

//        optionsUpdated.forEach(option -> {
//            if(option.getContracts() == null) {
//                option.setContracts(Set.of(contract));
//            } else {
//                option.getContracts().add(contract);
//            }
//        });

    }

}
