package javaschool.ecare.services.api;

import javaschool.ecare.dto.TariffDto;
import javaschool.ecare.entities.Option;
import javaschool.ecare.exceptions.NotValidOptionsException;
import javaschool.ecare.exceptions.OptionNotFoundException;
import javaschool.ecare.exceptions.TariffAlreadyExistsException;
import javaschool.ecare.exceptions.TariffNotFoundException;

import java.util.List;
import java.util.Set;

public interface TariffService {

    /**
     * Gets a list of the tariffs from the repository and converts each one to DTO.
     * @return a list of DTO tariffs.
     */
    List<TariffDto> getTariffs();

    /**
     * Marks the tariff as FOR DELETION, and now it cannot be chosen for any contracts.
     * @param id: the tariff's ID.
     * @throws TariffNotFoundException if the tariff is not found in the repository.
     */
    void archiveTariff(Long id) throws TariffNotFoundException;

    /**
     * Finds the tariff by ID in the repository and converts it to DTO.
     * @param id: the tariff's ID.
     * @return the tariff's DTO with the given ID.
     * @throws TariffNotFoundException if the tariff is not found in the repository.
     */
    TariffDto findTariffByIdTariff(Long id) throws TariffNotFoundException;

    /**
     * Checks that the array of the chosen new options contains all necessary additional options and no intersectional conflicting options.
     * @param options: an array of the new options for the tariff.
     * @return a set of valid options to be updated by the tariff.
     * @throws NotValidOptionsException
     * @throws OptionNotFoundException if the option is not found in the repository.
     */
    Set<Option> prepareTariffOptionsForUpdate(String[] options) throws NotValidOptionsException, OptionNotFoundException;

    /**
     * Updates the tariff's options with the given ones after their validation.
     * @param id: the tariff's ID.
     * @param options: an array of the new options to be updated by the tariff.
     * @throws TariffNotFoundException if the tariff is not found in the repository.
     * @throws OptionNotFoundException if the option is not found in the repository.
     * @throws NotValidOptionsException
     */
    void updateTariffOptions(Long id, String[] options) throws TariffNotFoundException, OptionNotFoundException, NotValidOptionsException;

    /**
     * Creates a new tariff in the repository having converting data from DTO to Tariff class firstly.
     * @param dto: the tariff's DTO.
     * @throws TariffAlreadyExistsException if the tariff already exists in the repository.
     */
    void addNewTariff(TariffDto dto) throws TariffAlreadyExistsException;
}
