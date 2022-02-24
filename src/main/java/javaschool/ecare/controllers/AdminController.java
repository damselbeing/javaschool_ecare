package javaschool.ecare.controllers;

import javaschool.ecare.services.api.ClientService;
import javaschool.ecare.services.api.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("view-clients")
    public String viewClients(Model model) {
        model.addAttribute("clients", clientService.getClients());
        return "admin/view-clients";
    }

    @GetMapping("view-contracts")
    public String viewContracts(Model model) {
        model.addAttribute("contracts", contractService.getContracts());
        return "admin/view-contracts";
    }




}