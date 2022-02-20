package javaschool.ecare.converters;

import javaschool.ecare.dto.ClientDto;
import javaschool.ecare.entities.Client;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClientConverter {

    public ClientDto entityToDto(Client client) {

        ClientDto dto = new ClientDto();
        dto.setId(client.getId());
        dto.setName(client.getName());
        dto.setLastName(client.getLastName());
        dto.setBirthDate(client.getBirthDate());
        dto.setPassport(client.getPassport());
        dto.setAddress(client.getAddress());
        dto.setEmail(client.getEmail());
        dto.setPassword(client.getPassword());

        return dto;
    }

    public List<ClientDto> entityToDto(List<Client> client) {

        return client.stream()
                .map(x -> entityToDto(x))
                .collect(Collectors.toList());
    }

    public Client dtoToEntity(ClientDto dto) {

        Client client = new Client();
        client.setId(dto.getId());
        client.setName(dto.getName());
        client.setLastName(dto.getLastName());
        client.setBirthDate(dto.getBirthDate());
        client.setPassport(dto.getPassport());
        client.setAddress(dto.getAddress());
        client.setEmail(dto.getEmail());
        client.setPassword(dto.getPassword());

        return client;
    }

    public List<Client> dtoToEntity(List<ClientDto> dto) {

        return dto.stream()
                .map(x -> dtoToEntity(x))
                .collect(Collectors.toList());
    }


}
