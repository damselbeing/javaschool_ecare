package javaschool.ecare.services.impl;

import javaschool.ecare.dto.OptionDto;
import javaschool.ecare.entities.Option;
import javaschool.ecare.entities.Tariff;
import javaschool.ecare.exceptions.NotValidOptionsException;
import javaschool.ecare.exceptions.OptionNotFoundException;
import javaschool.ecare.repositories.OptionRepository;
import javaschool.ecare.services.api.OptionService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Service
public class OptionServiceImpl implements OptionService {

    private final OptionRepository optionRepository;
    private final ModelMapper mapper;

    @Autowired
    public OptionServiceImpl(OptionRepository optionRepository, ModelMapper mapper) {
        this.optionRepository = optionRepository;
        this.mapper = mapper;
    }

    /**
     * {@inheritdoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<OptionDto> getOptions() {
        return optionRepository.findAll().stream()
                .map(option -> mapper.map(option, OptionDto.class))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritdoc}
     */
    @Transactional(readOnly = true)
    @Override
    public OptionDto findOptionByIdOption(Long idOption) throws OptionNotFoundException {
        return optionRepository.findOptionByIdOption(idOption)
                .map(option -> mapper.map(option, OptionDto.class))
                .orElseThrow(OptionNotFoundException::new);
    }

    /**
     * {@inheritdoc}
     */
    @Override
    public void validateOptions(String[] optionsAdditional, String[] optionsConflicting)
            throws NotValidOptionsException {
        for (int i = 0; i < optionsConflicting.length; i++) {
            if (Arrays.stream(optionsAdditional).anyMatch(optionsConflicting[i]::equals)) {
                log.error("Additional and conflicting options were chosen not correctly.");
                throw new NotValidOptionsException();
            }
        }
    }

    /**
     * {@inheritdoc}
     */
    @Transactional
    @Override
    public void updateOption(Long idOption,
                             String[] optionsAdditional,
                             String[] optionsConflicting)
            throws OptionNotFoundException, NotValidOptionsException {
        if(optionsAdditional != null && optionsConflicting != null) {
            validateOptions(optionsAdditional, optionsConflicting);
        }

        Option optionMain = optionRepository
                .findOptionByIdOption(idOption)
                .orElseThrow(OptionNotFoundException::new);
        Set<Option> optionsAdditionalUpdated = new HashSet<>();
        Set<Option> optionsConflictingUpdated = new HashSet<>();

        if(optionsAdditional != null) {
            for(int i = 0; i < optionsAdditional.length; i++) {
                Long optionId = Long.valueOf(optionsAdditional[i]);
                Option option = optionRepository
                        .findOptionByIdOption(optionId)
                        .orElseThrow(OptionNotFoundException::new);
                optionsAdditionalUpdated.add(option);
            }
        }

        if(optionsConflicting != null) {
            for(int i = 0; i < optionsConflicting.length; i++) {
                Long optionId = Long.valueOf(optionsConflicting[i]);
                Option option = optionRepository
                        .findOptionByIdOption(optionId)
                        .orElseThrow(OptionNotFoundException::new);
                optionsConflictingUpdated.add(option);
            }
        }

        optionMain.setConflictingOptions(optionsConflictingUpdated);
        optionMain.setAdditionalOptions(optionsAdditionalUpdated);

        Set<Tariff> tariffsToBeUpdated = optionMain.getTariffs();
        if (tariffsToBeUpdated != null) {
            tariffsToBeUpdated.forEach(tUp-> tUp.setMarkedForUpdate(true));
        }
    }

}
