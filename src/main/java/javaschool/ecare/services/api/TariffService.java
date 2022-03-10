package javaschool.ecare.services.api;

import javaschool.ecare.dto.ClientDto;
import javaschool.ecare.dto.ContractDto;
import javaschool.ecare.dto.TariffDto;
import javaschool.ecare.exceptions.ClientNotFoundException;
import javaschool.ecare.exceptions.TariffNotFoundException;

import java.util.List;

public interface TariffService {

    List<TariffDto> getTariffs();
    void archive(Long id) throws TariffNotFoundException;
}