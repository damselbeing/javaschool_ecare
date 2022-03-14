package javaschool.ecare.services.impl;

import javaschool.ecare.dto.OptionDto;
import javaschool.ecare.entities.Option;
import javaschool.ecare.exceptions.OptionNotFoundException;
import javaschool.ecare.repositories.OptionRepository;
import javaschool.ecare.services.api.OptionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    @Transactional
    public void updateAdditionalOptions(Long id, String[] options) throws OptionNotFoundException {
        Option optionMain = optionRepository.findOptionByIdOption(id).orElseThrow(OptionNotFoundException::new);
        Set<Option> optionsUpdated = new HashSet<>();

        if(options != null) {
            for(int i = 0; i < options.length; i++) {
                Long optionId = Long.valueOf(options[i]);
                Option option = optionRepository.findOptionByIdOption(optionId).orElseThrow(OptionNotFoundException::new);
                optionsUpdated.add(option);
            }
        }

        optionMain.setAdditionalOptions(optionsUpdated);
    }

    @Transactional
    public void updateConflictingOptions(Long id, String[] options) throws OptionNotFoundException {
        Option optionMain = optionRepository.findOptionByIdOption(id).orElseThrow(OptionNotFoundException::new);
        Set<Option> optionsUpdated = new HashSet<>();

        if(options != null) {
            for(int i = 0; i < options.length; i++) {
                Long optionId = Long.valueOf(options[i]);
                Option option = optionRepository.findOptionByIdOption(optionId).orElseThrow(OptionNotFoundException::new);
                optionsUpdated.add(option);
            }
        }

        optionMain.setConflictingOptions(optionsUpdated);
    }

}
