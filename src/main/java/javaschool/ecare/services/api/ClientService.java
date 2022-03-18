package javaschool.ecare.services.api;

import javaschool.ecare.dto.ClientDto;
import javaschool.ecare.entities.Client;
import javaschool.ecare.exceptions.ClientNotFoundException;
import javaschool.ecare.exceptions.ContractNotFoundException;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    @Transactional
    ClientDto findClientByIdClient(Long id) throws ClientNotFoundException;

    List<ClientDto> getClients();

    void addNewClient(ClientDto dto);

    ClientDto findClientByContract(String number) throws ClientNotFoundException, ContractNotFoundException;

}
