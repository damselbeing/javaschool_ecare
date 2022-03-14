package javaschool.ecare.services.impl;

import javaschool.ecare.dto.ContractDto;
import javaschool.ecare.entities.Contract;
import javaschool.ecare.exceptions.ClientNotFoundException;
import javaschool.ecare.repositories.ContractRepository;
import javaschool.ecare.services.api.ContractService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final ModelMapper mapper;

    @Autowired
    public ContractServiceImpl(ContractRepository contractRepository, ModelMapper mapper) {
        this.contractRepository = contractRepository;
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

}
