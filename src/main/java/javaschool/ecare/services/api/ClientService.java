package javaschool.ecare.services.api;

import javaschool.ecare.dto.ClientDto;
import javaschool.ecare.exceptions.ClientAlreadyExistsException;
import javaschool.ecare.exceptions.ClientNotFoundException;
import javaschool.ecare.exceptions.ContractNotFoundException;
import javaschool.ecare.exceptions.PasswordConfirmationFailedException;

import java.util.List;

public interface ClientService {

    /**
     * Finds the client by ID in the repository and converts it to DTO.
     * @param id: the client's ID.
     * @return the client's DTO with the given ID.
     * @throws ClientNotFoundException if the client is not found in the repository.
     */
    ClientDto findClientByIdClient(Long id) throws ClientNotFoundException;

    /**
     * Gets a list of clients from the repository and converts each one to DTO.
     * @return a list of DTO clients.
     */
    List<ClientDto> getClients();

    /**
     * Finds the client by Email in the repository and converts it to DTO.
     * @param email: the client's email.
     * @return the client's DTO with the given email.
     * @throws ClientNotFoundException if the client is not found in the repository.
     */
    ClientDto findClientByEmail(String email) throws ClientNotFoundException;

    /**
     * Creates a new client in the repository having converting data from DTO to Client class firstly.
     * @param dto: the client's DTO.
     * @throws ClientAlreadyExistsException if the client with the given passport or email already exists.
     * @throws PasswordConfirmationFailedException if the password has been inserted incorrectly in the field.
     */
    void registerNewClient(ClientDto dto) throws ClientAlreadyExistsException, PasswordConfirmationFailedException;

    /**
     * Finds the client by Contract in the repository and converts it to DTO.
     * @param number: the contract's number.
     * @return the client's DTO with the given contract.
     * @throws ClientNotFoundException if the client is not found in the repository.
     * @throws ContractNotFoundException if the contract is not found in the repository.
     */
    ClientDto findClientByContract(String number) throws ClientNotFoundException, ContractNotFoundException;

}
