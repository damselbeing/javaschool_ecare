package javaschool.ecare.services.impl;

import javaschool.ecare.dto.TariffDto;
import javaschool.ecare.entities.Option;
import javaschool.ecare.entities.Tariff;
import javaschool.ecare.exceptions.NotValidOptionsException;
import javaschool.ecare.exceptions.OptionNotFoundException;
import javaschool.ecare.exceptions.TariffAlreadyExistsException;
import javaschool.ecare.exceptions.TariffNotFoundException;
import javaschool.ecare.repositories.OptionRepository;
import javaschool.ecare.repositories.TariffRepository;
import javaschool.ecare.services.api.TariffService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
public class TariffServiceImpl implements TariffService {

    private final TariffRepository tariffRepository;
    private final OptionRepository optionRepository;
    private final ModelMapper mapper;

    @Autowired
    public TariffServiceImpl(TariffRepository tariffRepository,
                             OptionRepository optionRepository,
                             ModelMapper mapper) {
        this.tariffRepository = tariffRepository;
        this.optionRepository = optionRepository;
        this.mapper = mapper;
    }

    /**
     * {@inheritdoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<TariffDto> getTariffs() {
        return tariffRepository.findAll().stream()
                .map(tariff -> mapper.map(tariff, TariffDto.class))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritdoc}
     */
    @Transactional
    @Override
    public void archiveTariff(Long idTariff) throws TariffNotFoundException {
        Tariff tariff = tariffRepository
                .findTariffByIdTariff(idTariff)
                .orElseThrow(TariffNotFoundException::new);
        tariff.setArchived(true);
    }

    /**
     * {@inheritdoc}
     */
    @Transactional(readOnly = true)
    @Override
    public TariffDto findTariffByIdTariff(Long idTariff) throws TariffNotFoundException {
        return tariffRepository.findTariffByIdTariff(idTariff)
                .map(tariff -> mapper.map(tariff, TariffDto.class))
                .orElseThrow(TariffNotFoundException::new);
    }

    /**
     * {@inheritdoc}
     */
    @Transactional
    @Override
    public void addNewTariff(TariffDto dto) throws TariffAlreadyExistsException {
        Tariff tariff = mapper.map(dto, Tariff.class);

        if(dto.getName() != null && dto.getPrice() >= 0) {

            if(tariffRepository.findTariffByName(dto.getName().toUpperCase()).isPresent()) {
                log.error("Tariff can't be saved because this tariff name is taken: " + dto.getName());
                throw new TariffAlreadyExistsException();
            }

            tariff.setName(tariff.getName().toUpperCase());
            tariffRepository.save(tariff);
        }
    }

    /**
     * {@inheritdoc}
     */
    @Transactional
    @Override
    public Set<Option> prepareTariffOptionsForUpdate(String[] options)
            throws NotValidOptionsException, OptionNotFoundException {
        Set<Option> optionsUpdated = new HashSet<>();

        if(options != null) {
            for(int i = 0; i < options.length; i++) {
                Long optionId = Long.valueOf(options[i]);
                Option optionSelected = optionRepository
                        .findOptionByIdOption(optionId)
                        .orElseThrow(OptionNotFoundException::new);

                List<String> addOpts = new ArrayList<>();
                optionSelected.getAdditionalOptions().stream()
                        .forEach(opt -> addOpts.add(String.valueOf(opt.getIdOption())));

                List<String> conflOpts = new ArrayList<>();
                optionSelected.getConflictingOptions().stream()
                        .forEach(opt -> conflOpts.add(String.valueOf(opt.getIdOption())));

                if((addOpts == null || Arrays.asList(options).containsAll(addOpts))
                        &&
                        (conflOpts == null || !Arrays.asList(options)
                                .stream()
                                .anyMatch(o -> conflOpts.contains(o)))) {
                    optionsUpdated.add(optionSelected);
                } else {
                    log.error("Additional and conflicting options were chosen not correctly.");
                    throw new NotValidOptionsException();
                }
            }
        }

        return optionsUpdated;
    }

    /**
     * {@inheritdoc}
     */
    @Transactional
    @Override
    public void updateTariffOptions(Long idTariff, String[] options)
            throws TariffNotFoundException, OptionNotFoundException, NotValidOptionsException {
        Tariff tariff = tariffRepository
                .findTariffByIdTariff(idTariff)
                .orElseThrow(TariffNotFoundException::new);
        Set<Option> optionsUpdated = prepareTariffOptionsForUpdate(options);
        tariff.setOptions(optionsUpdated);
        if(tariff.isMarkedForUpdate() == true) {
            tariff.setMarkedForUpdate(false);
        }
    }

}

