package javaschool.ecare.services.api;

import javaschool.ecare.dto.TariffDto;
import javaschool.ecare.exceptions.OptionNotFoundException;
import javaschool.ecare.exceptions.TariffNotFoundException;

import java.util.List;

public interface TariffService {

    List<TariffDto> getTariffs();
    void archiveTariff(Long id) throws TariffNotFoundException;
    TariffDto findTariffByIdTariff(Long id) throws TariffNotFoundException;
    void updateTariff(Long id, String[] options) throws TariffNotFoundException, OptionNotFoundException;
}
