package javaschool.ecare.services.impl;

import javaschool.ecare.dto.ContractDto;
import javaschool.ecare.dto.OptionDto;
import javaschool.ecare.dto.TariffDto;
import javaschool.ecare.entities.Contract;
import javaschool.ecare.exceptions.ClientNotFoundException;
import javaschool.ecare.exceptions.OptionNotFoundException;
import javaschool.ecare.exceptions.TariffNotFoundException;
import javaschool.ecare.repositories.ContractRepository;
import javaschool.ecare.repositories.OptionRepository;
import javaschool.ecare.services.api.ContractService;
import javaschool.ecare.services.api.OptionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OptionServiceImpl implements OptionService {

    private final OptionRepository optionRepository;
    private final ModelMapper mapper;

    @Autowired
    public OptionServiceImpl(OptionRepository optionRepository, ModelMapper mapper) {
        this.optionRepository = optionRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    @Override
    public List<OptionDto> getOptions() {
        return optionRepository.findAll().stream()
                .map(option -> mapper.map(option, OptionDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public OptionDto findOptionByIdOption(Long id) throws OptionNotFoundException {
        return optionRepository.findOptionByIdOption(id)
                .map(option -> mapper.map(option, OptionDto.class))
                .orElseThrow(OptionNotFoundException::new);
    }

}
