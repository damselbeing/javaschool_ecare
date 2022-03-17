package javaschool.ecare.controllers;

import javaschool.ecare.dto.ClientDto;
import javaschool.ecare.exceptions.ClientNotFoundException;
import javaschool.ecare.services.api.ClientService;
import javaschool.ecare.services.api.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/")
public class ClientController {

    private final ClientService clientService;
    private final ContractService contractService;

    @Autowired
    public ClientController(ClientService clientService, ContractService contractService) {
        this.clientService = clientService;
        this.contractService = contractService;
    }

    @GetMapping("welcome")
    public String showWelcomePage() {
        return "welcome";
    }

    @GetMapping("registration")
    public String showRegistrationForm(Model model) {
        ClientDto dto = new ClientDto();
        model.addAttribute("client", dto);
        return "registration";
    }

    @PostMapping("registration")
    public String registerNewClient(@ModelAttribute("client") ClientDto dto) {
        clientService.addNewClient(dto);
        return "success";
    }

    @GetMapping("account/{id}")
    public String showPersonalAccount(@PathVariable(value = "id") Long id, Model model) throws ClientNotFoundException {
        model.addAttribute("client", clientService.findClientByIdClient(id));
        return "account";
    }

    @PostMapping("blockContract/{idClient}/{idContract}")
    public String blockContract(@PathVariable(value = "idClient") Long idClient,
                                @PathVariable(value = "idContract") Long idContract)
            throws ClientNotFoundException {
        contractService.blockByClient(idContract);
        return "redirect:/account/{idClient}";
    }


}