package javaschool.ecare.controllers;

import javaschool.ecare.dto.ClientDto;
import javaschool.ecare.services.api.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("welcome")
    public String showWelcomePage() {
        return "welcome";
    }

    @GetMapping("view-clients")
    public String viewClients(Model model) {
        model.addAttribute("clients", clientService.getClients());
        return "view-clients";
    }

    @GetMapping("register-new-client")
    public String showRegistrationForm(Model model) {
        ClientDto dto = new ClientDto();
        model.addAttribute("client", dto);
        return "register-new-client";
    }

    @PostMapping("register-new-client")
    public String registerNewClient(@ModelAttribute("client") ClientDto dto) {
        clientService.addNewClient(dto);
        return "success";
    }


}