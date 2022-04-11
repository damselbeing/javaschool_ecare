package javaschool.ecare.services.api;

import javaschool.ecare.dto.OptionDto;
import javaschool.ecare.exceptions.NotValidOptionsException;
import javaschool.ecare.exceptions.OptionNotFoundException;

import java.util.List;

public interface OptionService {

    /**
     * Gets a list of the options from the repository and converts each one to DTO.
     * @return a list of DTO options.
     */
    List<OptionDto> getOptions();

    /**
     * Finds the option by ID in the repository and converts it to DTO.
     * @param id: the option's ID.
     * @return the option's DTO with the given ID.
     * @throws OptionNotFoundException if the option is not found in the repository.
     */
    OptionDto findOptionByIdOption(Long id) throws OptionNotFoundException;

    /**
     * Updates the additional and conflicting options for the main option.
     * @param id: the main option's ID.
     * @param optionsAdditional: an array of the additional options to be added to the main option.
     * @param optionsConflicting: an array of the conflicting options to be added to the main option.
     * @throws OptionNotFoundException if the option is not found in the repository.
     * @throws NotValidOptionsException if the additional and conflicting options have been chosen not correctly.
     */
    void updateOption(Long id, String[] optionsAdditional, String[] optionsConflicting) throws OptionNotFoundException, NotValidOptionsException;

    /**
     * Checks if there are any intersections between additional and conflicting options.
     * @param optionsAdditional: an array of the additional options to be compared with the conflicting ones.
     * @param optionsConflicting: an array of the conflicting options to be compared with the additional option.
     * @throws NotValidOptionsException if the additional and conflicting options have been chosen not correctly.
     */
    void validateOptions(String[] optionsAdditional, String[] optionsConflicting) throws NotValidOptionsException;

}
