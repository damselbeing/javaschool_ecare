package javaschool.ecare.services.api;

import javaschool.ecare.dto.ContractDto;
import javaschool.ecare.dto.OptionDto;
import javaschool.ecare.dto.TariffDto;
import javaschool.ecare.exceptions.ClientNotFoundException;

import java.util.List;

public interface OptionService {

    List<OptionDto> getOptions();
}
