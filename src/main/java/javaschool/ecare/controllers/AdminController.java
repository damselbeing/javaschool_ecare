package javaschool.ecare.controllers;

import javaschool.ecare.exceptions.ClientNotFoundException;
import javaschool.ecare.services.api.ClientService;
import javaschool.ecare.services.api.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(path = "/admin/")
public class AdminController {

    private final ClientService clientService;
    private final ContractService contractService;

    @Autowired
    public AdminController(ClientService clientService, ContractService contractService) {

        this.clientService = clientService;
        this.contractService = contractService;
    }

    @GetMapping("clients")
    public String viewClients(Model model) {
        model.addAttribute("clients", clientService.getClients());
        return "admin/view-clients";
    }

    @GetMapping("contracts")
    public String viewContracts(Model model) {
        model.addAttribute("contracts", contractService.getContracts());
        return "admin/view-contracts";
    }

    @GetMapping("searchClientByContract")
    public String showClientByContract(@RequestParam(name = "contractNumber") String contractNumber, Model model) throws ClientNotFoundException {
        model.addAttribute("client", clientService.findClientByContract(contractNumber));
        return "admin/view-profile";
    }

    @GetMapping("searchClientByPassport")
    public String showClientByPassport(@RequestParam(name = "passportNumber") String passportNumber, Model model) throws ClientNotFoundException {
        model.addAttribute("client", clientService.findClientByPassport(passportNumber));
        return "admin/view-profile";
    }


}