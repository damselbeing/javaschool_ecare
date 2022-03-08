package javaschool.ecare.controllers;

import javaschool.ecare.dto.ClientDto;
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

import java.util.ArrayList;
import java.util.List;

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
    public String viewClients(@RequestParam(required = false, name = "contractNumber") String contractNumber, Model model) throws ClientNotFoundException {
        if(contractNumber == null) {
            model.addAttribute("clients", clientService.getClients());
        } else {
            List<ClientDto> listOfOne = new ArrayList<>();
            listOfOne.add(clientService.findClientByContract(contractNumber));
            model.addAttribute("clients", listOfOne);
        }
        return "admin/view-clients";
    }

    @GetMapping("contractProfile/{id}")
    public String showContractProfile(@PathVariable(value = "id") Long id, Model model) throws ClientNotFoundException {
        model.addAttribute("contract", contractService.findContractByIdContract(id));
        return "admin/contract-profile";
    }

}