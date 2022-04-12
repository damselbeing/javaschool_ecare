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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TariffServiceTest {

    @Mock
    OptionRepository optionRepository;

    @Mock
    TariffRepository tariffRepository;

    private ModelMapper modelMapper;
    private TariffServiceImpl tariffService;
    private Tariff tariff1;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        tariffService = new TariffServiceImpl(tariffRepository, optionRepository, modelMapper);
        tariff1 = new Tariff(1L, "Tariff1", 10.00, false, false);
        tariffRepository.save(tariff1);
    }


    @Test
    void shouldGetTariffs() {
        when(tariffRepository.findAll()).thenReturn(new ArrayList<>());
        tariffService.getTariffs();
        verify(tariffRepository).findAll();
    }

    @Test
    void shouldArchiveTariff() throws TariffNotFoundException {
        when(tariffRepository.findTariffByIdTariff(1L))
                .thenReturn(Optional.ofNullable(tariff1));
        tariffService.archiveTariff(1L);
        assertEquals(true, tariff1.isArchived());
    }

    @Test
    void shouldFindTariffByIdTariff() throws TariffNotFoundException {
        when(tariffRepository.findTariffByIdTariff(1L))
                .thenReturn(Optional.ofNullable(tariff1));

        Tariff actual = modelMapper.map(tariffService.findTariffByIdTariff(1L), Tariff.class);
        Tariff expected = tariff1;

        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindTariffByIdTariff() {
        assertThrows(TariffNotFoundException.class,
                ()->{
                    tariffService.findTariffByIdTariff(2L);
                });
    }

    @Test
    void shouldAddNewTariff() throws TariffAlreadyExistsException, TariffNotFoundException {
        Tariff tariff2 = new Tariff(2L, "Tariff2", 30.00, false, false);
        TariffDto dto = modelMapper.map(tariff2, TariffDto.class);

        assertFalse(tariffRepository.findTariffByName(dto.getName().toUpperCase()).isPresent());

        tariffService.addNewTariff(dto);

        TariffDto expected = dto;

        when(tariffRepository.findTariffByIdTariff(2L))
                .thenReturn(Optional.ofNullable(tariff2));

        TariffDto actual = tariffService.findTariffByIdTariff(2L);

        assertEquals(expected, actual);

    }

    @Test
    void shouldPrepareTariffOptionsForUpdate() throws OptionNotFoundException, NotValidOptionsException {

        Option option1 = new Option(1L, "Option1", 10.00, 20.00);
        Option option2 = new Option(2L, "Option2", 30.00, 40.00);
        Option option3 = new Option(3L, "Option3", 50.00, 60.00);
        Option option4 = new Option(4L, "Option4", 70.00, 80.00);
        Option option5 = new Option(5L, "Option5", 90.00, 100.00);
        Option option6 = new Option(6L, "Option6", 110.00, 120.00);

        String[] options = {"1", "2", "3"};

        Set<Option> optionSet = new HashSet<>();
        optionSet.add(option1);
        optionSet.add(option2);
        optionSet.add(option3);

        when(optionRepository.findOptionByIdOption(1L))
                .thenReturn(Optional.ofNullable(option1));

        when(optionRepository.findOptionByIdOption(2L))
                .thenReturn(Optional.ofNullable(option2));

        when(optionRepository.findOptionByIdOption(3L))
                .thenReturn(Optional.ofNullable(option3));

        Set<Option> addOptsFor1 = new HashSet<>();
        addOptsFor1.add(option3);
        option1.setAdditionalOptions(addOptsFor1);

        Set<Option> conflOptsFor1 = new HashSet<>();
        conflOptsFor1.add(option4);
        option1.setConflictingOptions(conflOptsFor1);

        Set<Option> addOptsFor2 = new HashSet<>();
        addOptsFor2.add(option1);
        option2.setAdditionalOptions(addOptsFor2);

        Set<Option> conflOptsFor2 = new HashSet<>();
        conflOptsFor2.add(option5);
        option2.setConflictingOptions(conflOptsFor2);

        Set<Option> addOptsFor3 = new HashSet<>();
        addOptsFor3.add(option2);
        option3.setAdditionalOptions(addOptsFor3);

        Set<Option> conflOptsFor3 = new HashSet<>();
        conflOptsFor3.add(option6);
        option3.setConflictingOptions(conflOptsFor3);

        assertTrue(optionSet.containsAll(tariffService.prepareTariffOptionsForUpdate(options)));

    }

//    @Test
//    void shouldUpdateTariffOptions() {
//
//    }
}