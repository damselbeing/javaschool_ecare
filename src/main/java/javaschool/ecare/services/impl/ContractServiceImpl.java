package javaschool.ecare.services.impl;

import javaschool.ecare.converters.ContractConverter;
import javaschool.ecare.dto.ContractDto;
import javaschool.ecare.entities.Contract;
import javaschool.ecare.repositories.ContractRepository;
import javaschool.ecare.services.api.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final ContractConverter converter;

    @Autowired
    public ContractServiceImpl(ContractRepository contractRepository, ContractConverter converter) {
        this.contractRepository = contractRepository;
        this.converter = converter;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ContractDto> getContracts() {
        List<Contract> getAll = contractRepository.findAll();
        return converter.entityToDto(getAll);
    }

}
