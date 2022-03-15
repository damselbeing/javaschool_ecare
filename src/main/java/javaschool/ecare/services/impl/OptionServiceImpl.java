package javaschool.ecare.services.impl;

import javaschool.ecare.dto.OptionDto;
import javaschool.ecare.entities.Option;
import javaschool.ecare.exceptions.NotValidOptionsException;
import javaschool.ecare.exceptions.OptionNotFoundException;
import javaschool.ecare.repositories.OptionRepository;
import javaschool.ecare.services.api.OptionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
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
    @Override
    public void validateOptionsSets(String[] optionsAdditional, String[] optionsConflicting) throws NotValidOptionsException {
        for (int i = 0; i < optionsConflicting.length; i++) {
            if (Arrays.stream(optionsAdditional).anyMatch(optionsConflicting[i]::equals)) {
                throw new NotValidOptionsException();
            }
        }
    }

    @Transactional
    @Override
    public void updateOption(Long id, String[] optionsAdditional, String[] optionsConflicting) throws OptionNotFoundException, NotValidOptionsException {
        validateOptionsSets(optionsAdditional, optionsConflicting);

        Option optionMain = optionRepository.findOptionByIdOption(id).orElseThrow(OptionNotFoundException::new);
        Set<Option> optionsAdditionalUpdated = new HashSet<>();
        Set<Option> optionsConflictingUpdated = new HashSet<>();

        if(optionsAdditional != null) {
            for(int i = 0; i < optionsAdditional.length; i++) {
                Long optionId = Long.valueOf(optionsAdditional[i]);
                Option option = optionRepository.findOptionByIdOption(optionId).orElseThrow(OptionNotFoundException::new);
                optionsAdditionalUpdated.add(option);
            }
        }

        if(optionsConflicting != null) {
            for(int i = 0; i < optionsConflicting.length; i++) {
                Long optionId = Long.valueOf(optionsConflicting[i]);
                Option option = optionRepository.findOptionByIdOption(optionId).orElseThrow(OptionNotFoundException::new);
                optionsConflictingUpdated.add(option);
            }
        }

        optionMain.setConflictingOptions(optionsConflictingUpdated);
        optionMain.setAdditionalOptions(optionsAdditionalUpdated);
    }



}
