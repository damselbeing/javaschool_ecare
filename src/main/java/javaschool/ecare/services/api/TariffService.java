package javaschool.ecare.services.api;

import javaschool.ecare.dto.TariffDto;
import javaschool.ecare.entities.Option;
import javaschool.ecare.exceptions.NotValidOptionsException;
import javaschool.ecare.exceptions.OptionNotFoundException;
import javaschool.ecare.exceptions.TariffNotFoundException;

import java.util.List;
import java.util.Set;

public interface TariffService {

    List<TariffDto> getTariffs();
    void archiveTariff(Long id) throws TariffNotFoundException;
    TariffDto findTariffByIdTariff(Long id) throws TariffNotFoundException;
    Set<Option> changeTariffOptions(String[] options) throws NotValidOptionsException, OptionNotFoundException;
    void updateTariff(Long id, String[] options) throws TariffNotFoundException, OptionNotFoundException, NotValidOptionsException;

    void addNewTariff(TariffDto dto);
}
