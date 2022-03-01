package javaschool.ecare.services.api;

import javaschool.ecare.dto.ClientDto;
import javaschool.ecare.entities.Client;
import javaschool.ecare.exceptions.ClientNotFoundException;
import org.springframework.expression.spel.ast.OpAnd;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    List<ClientDto> getClients();

    void addNewClient(ClientDto dto);

    ClientDto findClientByPassport(String passport) throws ClientNotFoundException;

    ClientDto findClientByContract(String contract) throws ClientNotFoundException;

}
