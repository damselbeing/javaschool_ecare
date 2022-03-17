package javaschool.ecare.services.impl;

import javaschool.ecare.dto.ContractDto;
import javaschool.ecare.entities.Contract;
import javaschool.ecare.entities.Option;
import javaschool.ecare.exceptions.ClientNotFoundException;
import javaschool.ecare.exceptions.NotValidOptionsException;
import javaschool.ecare.exceptions.OptionNotFoundException;
import javaschool.ecare.repositories.ContractRepository;
import javaschool.ecare.repositories.OptionRepository;
import javaschool.ecare.services.api.ContractService;
import javaschool.ecare.services.api.TariffService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final OptionRepository optionRepository;
    private final TariffService tariffService;
    private final ModelMapper mapper;

    @Autowired
    public ContractServiceImpl(ContractRepository contractRepository, OptionRepository optionRepository, TariffService tariffService, ModelMapper mapper) {
        this.contractRepository = contractRepository;
        this.optionRepository = optionRepository;
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
    public void unblockByAdmin(Long id) throws ClientNotFoundException {
        Contract contract = contractRepository.findContractByIdContract(id).orElseThrow(ClientNotFoundException::new);
        contract.setBlockedByAdmin(false);
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
