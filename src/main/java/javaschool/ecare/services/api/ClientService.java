package javaschool.ecare.services.api;

import javaschool.ecare.dto.ClientDto;
import javaschool.ecare.exceptions.ClientAlreadyExistsException;
import javaschool.ecare.exceptions.ClientNotFoundException;
import javaschool.ecare.exceptions.ContractNotFoundException;
import javaschool.ecare.exceptions.PasswordConfirmationFailedException;

import java.util.List;

public interface ClientService {

    ClientDto findClientByIdClient(Long id) throws ClientNotFoundException;

    List<ClientDto> getClients();

    ClientDto findClientByEmail(String email) throws ClientNotFoundException;

    void registerNewClient(ClientDto dto) throws ClientAlreadyExistsException, PasswordConfirmationFailedException;

    ClientDto findClientByContract(String number) throws ClientNotFoundException, ContractNotFoundException;

}
