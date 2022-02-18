package javaschool.ecare.controllers;

import javaschool.ecare.entities.Client;
import javaschool.ecare.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "client")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {

        this.clientService = clientService;
    }

    @GetMapping
    public List<Client> getClients() {

        return clientService.getClients();
    }

}