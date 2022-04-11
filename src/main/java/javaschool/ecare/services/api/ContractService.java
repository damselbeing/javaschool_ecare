package javaschool.ecare.services.api;

import javaschool.ecare.dto.ContractDto;
import javaschool.ecare.exceptions.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;

public interface ContractService {

    /**
     * Gets a list of the contracts from the repository and converts each one to DTO.
     * @return a list of DTO contracts.
     */
    List<ContractDto> getContracts();

    /**
     * Finds the contract by ID in the repository and converts it to DTO.
     * @param id: the contract's ID.
     * @return the contract's DTO with the given ID.
     * @throws ContractNotFoundException if the contract is not found in the repository.
     */
    ContractDto findContractByIdContract(Long id) throws ContractNotFoundException;

    /**
     * Admin blocks the contract, as the result no contract changes can be made anymore.
     * @param id: the contract's ID.
     * @throws ContractNotFoundException if the contract is not found in the repository.
     */
    void blockByAdmin(Long id) throws ContractNotFoundException;

    /**
     * Admin unblocks the contract, and the contract can be edited again.
     * @param id: the contract's ID.
     * @throws ContractNotFoundException if the contract is not found in the repository.
     */
    void unblockByAdmin(Long id) throws ContractNotFoundException;

    /**
     * Updates the contract options with the given ones.
     * @param id: the contract's ID.
     * @param options: new options for the contract.
     * @throws ContractNotFoundException if the contract is not found in the repository.
     * @throws OptionNotFoundException if the option is not found in the repository.
     * @throws NotValidOptionsException if the additional and conflicting options have been chosen not correctly.
     */
    void updateContractOptions(Long id, String[] options) throws ContractNotFoundException, OptionNotFoundException, NotValidOptionsException;

    /**
     * Client blocks the contract, as the result no contract changes can be made anymore.
     * @param id: the contract's ID.
     * @throws ContractNotFoundException if the contract is not found in the repository.
     */
    void blockByClient(Long id) throws ContractNotFoundException;

    /**
     * Client unblocks the contract, and the contract can be edited again.
     * @param id: the contract's ID.
     * @throws ContractNotFoundException if the contract is not found in the repository.
     */
    void unblockByClient(Long id) throws ContractNotFoundException;

    /**
     * Gets a set of numbers for creating a new contract instance in the repository.
     * @return a set of randomly generated contract numbers which are unique.
     */
    Set<String> getGeneratedNumbers();

    /**
     * Generates a contract number with a certain pattern.
     * @return a contract number generated according to a certain pattern.
     */
    String generateNumber();

    /**
     * Updates the contract's tariff with the given one, and sends a message with POP tariff to Epromo.
     * @param idContract: the contract's ID.
     * @param idTariff: the tariff's ID.
     * @throws ContractNotFoundException if the contract is not found in the repository.
     * @throws TariffNotFoundException if the tariff is not found in the repository.
     * @throws IOException if a message queue has not been created.
     * @throws TimeoutException if a message queue has not been created.
     */
    void updateContractTariff(Long idContract, String idTariff) throws ContractNotFoundException, TariffNotFoundException, IOException, TimeoutException;

    /**
     * Creates a new contract in the repository having converting data from DTO to Contract class firstly.
     * @param dto: the contract's DTO.
     * @param id: the client's ID.
     * @throws ClientNotFoundException if the client is not found in the repository.
     * @throws ContractNotFoundException if the contract is not found in the repository.
     */
    void addNewContract(ContractDto dto, Long id) throws ClientNotFoundException, ContractNotFoundException;

}
