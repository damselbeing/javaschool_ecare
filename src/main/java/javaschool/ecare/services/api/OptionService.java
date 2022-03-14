package javaschool.ecare.services.api;

import javaschool.ecare.dto.ContractDto;
import javaschool.ecare.dto.OptionDto;
import javaschool.ecare.dto.TariffDto;
import javaschool.ecare.exceptions.ClientNotFoundException;
import javaschool.ecare.exceptions.OptionNotFoundException;

import java.util.List;

public interface OptionService {

    List<OptionDto> getOptions();
    OptionDto findOptionByIdOption(Long id) throws OptionNotFoundException;
    void updateAdditionalOptions(Long id, String[] options) throws OptionNotFoundException;
    void updateConflictingOptions(Long id, String[] options) throws OptionNotFoundException;
}
