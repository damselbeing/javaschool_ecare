package javaschool.ecare.services.api;

import javaschool.ecare.dto.OptionDto;
import javaschool.ecare.exceptions.NotValidOptionsException;
import javaschool.ecare.exceptions.OptionNotFoundException;

import java.util.List;

public interface OptionService {

    List<OptionDto> getOptions();
    OptionDto findOptionByIdOption(Long id) throws OptionNotFoundException;
    void updateOption(Long id, String[] optionsAdditional, String[] optionsConflicting) throws OptionNotFoundException, NotValidOptionsException;
    void validateOptions(String[] optionsAdditional, String[] optionsConflicting) throws NotValidOptionsException;
}
