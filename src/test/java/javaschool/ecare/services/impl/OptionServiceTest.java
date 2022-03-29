package javaschool.ecare.services.impl;

import javaschool.ecare.entities.Option;
import javaschool.ecare.entities.Tariff;
import javaschool.ecare.exceptions.NotValidOptionsException;
import javaschool.ecare.exceptions.OptionNotFoundException;
import javaschool.ecare.repositories.OptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;


import java.util.*;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssumptions.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OptionServiceTest {


    @Mock
    OptionRepository optionRepository;

    private ModelMapper modelMapper;
    private OptionServiceImpl optionService;
    private Option option1;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        optionService = new OptionServiceImpl(optionRepository, modelMapper);
        option1 = new Option(1L, "Option1", 10.00, 20.00);

    }

    @Test
    void getOptions() {
        when(optionRepository.findAll()).thenReturn(new ArrayList<>());
        optionService.getOptions();
        verify(optionRepository).findAll();
    }

    @Test
    void shouldFindOptionByIdOption() throws OptionNotFoundException {
        when(optionRepository.findOptionByIdOption(1L))
                .thenReturn(Optional.ofNullable(option1));

        Option actual = modelMapper.map(optionService.findOptionByIdOption(1L), Option.class);
        Option expected = option1;

        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindOptionByIdOption() {
        assertThrows(OptionNotFoundException.class,
                ()->{
                    optionService.findOptionByIdOption(2L);
                });
    }

    @Test
    void shouldValidateOptions() {
        String[] optionsAdditional = {"add1", "add2"};
        String[] optionsConflicting = {"confl1", "confl2"};
        assertDoesNotThrow(
                () -> optionService.validateOptions(optionsAdditional, optionsConflicting)
        );

    }

    @Test
    void shouldNotValidateOptions() {
        String[] optionsAdditional = {"add1", "add2"};
        String[] optionsConflicting = {"add1", "confl2"};
        assertThrows(NotValidOptionsException.class,
                ()->{
                    optionService.validateOptions(optionsAdditional, optionsConflicting);
                });
    }

    @Test
    void shouldUpdateOption() throws OptionNotFoundException, NotValidOptionsException {
        String[] optionsAdditional = {"3"};
        String[] optionsConflicting = {"4"};

        Option option3 = new Option(3L, "Option3", 30.00, 40.00);
        Option option4 = new Option(4L, "Option4", 50.00, 60.00);

        when(optionRepository.findOptionByIdOption(1L))
                .thenReturn(Optional.ofNullable(option1));

        when(optionRepository.findOptionByIdOption(3L))
                .thenReturn(Optional.ofNullable(option3));

        when(optionRepository.findOptionByIdOption(4L))
                .thenReturn(Optional.ofNullable(option4));

        assertDoesNotThrow(
                () ->
                        optionService.updateOption(1L, optionsAdditional, optionsConflicting)
        );


        Tariff tariff = new Tariff(1L, "tariff1", 10.00, false, false);
        Set<Tariff> tariffs = new HashSet<>();
        tariffs.add(tariff);
        option1.setTariffs(tariffs);
        optionService.updateOption(1L, optionsAdditional, optionsConflicting);
        assertEquals(true, tariff.isMarkedForUpdate());

    }

    @Test
    void shouldNotUpdateOption() {
        String[] optionsAdditional = {"3"};
        String[] optionsConflicting = {"3"};

        assertThrows(NotValidOptionsException.class,
                ()->{
                    optionService.updateOption(1L, optionsAdditional, optionsConflicting);
                });

    }
}