package javaschool.ecare.services.impl;

import javaschool.ecare.dto.ClientDto;
import javaschool.ecare.dto.ContractDto;
import javaschool.ecare.dto.TariffDto;
import javaschool.ecare.entities.Client;
import javaschool.ecare.entities.Contract;
import javaschool.ecare.entities.Tariff;
import javaschool.ecare.exceptions.ClientNotFoundException;
import javaschool.ecare.repositories.ContractRepository;
import javaschool.ecare.repositories.OptionRepository;
import javaschool.ecare.repositories.TariffRepository;
import javaschool.ecare.services.api.ContractService;
import javaschool.ecare.services.api.OptionService;
import javaschool.ecare.services.api.TariffService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TariffServiceImpl implements TariffService {

    private final TariffRepository tariffRepository;
    private final ModelMapper mapper;

    @Autowired
    public TariffServiceImpl(TariffRepository tariffRepository, ModelMapper mapper) {
        this.tariffRepository = tariffRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    @Override
    public List<TariffDto> getTariffs() {
        List<Tariff> getAll = tariffRepository.findAll();
        return getAll.stream()
                .map(tariff -> mapper.map(tariff, TariffDto.class))
                .collect(Collectors.toList());
    }


}