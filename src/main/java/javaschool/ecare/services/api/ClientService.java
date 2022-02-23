package javaschool.ecare.services.api;

import javaschool.ecare.dto.ClientDto;

import java.util.List;

public interface ClientService {

    List<ClientDto> getClients();

    void addNewClient(ClientDto dto);

}
