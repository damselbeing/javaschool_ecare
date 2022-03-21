package javaschool.ecare.services.api;

import javaschool.ecare.dto.ClientDto;
import javaschool.ecare.exceptions.ClientNotFoundException;
import javaschool.ecare.exceptions.ContractNotFoundException;

import java.util.List;

public interface ClientService {

    ClientDto findClientByIdClient(Long id) throws ClientNotFoundException;

    List<ClientDto> getClients();

    ClientDto findClientByEmail(String email) throws ClientNotFoundException;

    boolean registerNewClient(ClientDto dto);

    ClientDto findClientByContract(String number) throws ClientNotFoundException, ContractNotFoundException;

}
