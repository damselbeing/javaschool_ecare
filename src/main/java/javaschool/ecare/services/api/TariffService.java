package javaschool.ecare.services.api;

import javaschool.ecare.dto.ClientDto;
import javaschool.ecare.dto.ContractDto;
import javaschool.ecare.dto.TariffDto;
import javaschool.ecare.exceptions.ClientNotFoundException;
import javaschool.ecare.exceptions.OptionNotFoundException;
import javaschool.ecare.exceptions.TariffNotFoundException;

import java.util.List;

public interface TariffService {

    List<TariffDto> getTariffs();
    void archive(Long id) throws TariffNotFoundException;
    TariffDto findTariffByIdTariff(Long id) throws TariffNotFoundException;
    void update(Long id, String[] options) throws TariffNotFoundException, OptionNotFoundException;;
}
