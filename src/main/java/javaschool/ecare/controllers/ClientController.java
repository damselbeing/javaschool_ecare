package javaschool.ecare.controllers;

import javaschool.ecare.dto.ClientDto;
import javaschool.ecare.exceptions.ClientNotFoundException;
import javaschool.ecare.exceptions.NotValidOptionsException;
import javaschool.ecare.exceptions.OptionNotFoundException;
import javaschool.ecare.exceptions.TariffNotFoundException;
import javaschool.ecare.repositories.TariffRepository;
import javaschool.ecare.services.api.ClientService;
import javaschool.ecare.services.api.ContractService;
import javaschool.ecare.services.api.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/")
public class ClientController {

    private final ClientService clientService;
    private final ContractService contractService;
    private final TariffService tariffService;

    @Autowired
    public ClientController(ClientService clientService, ContractService contractService, TariffService tariffService) {
        this.clientService = clientService;
        this.contractService = contractService;
        this.tariffService = tariffService;
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
    public String showPersonalAccount(
            @PathVariable(value = "id") Long id,
            Model model
    ) throws ClientNotFoundException {
        model.addAttribute("client", clientService.findClientByIdClient(id));
        model.addAttribute("tariffs", tariffService.getTariffs());
        return "account";
    }

    @PostMapping("blockContract/{idClient}/{idContract}")
    public String blockContract(@PathVariable(value = "idClient") Long idClient,
                                @PathVariable(value = "idContract") Long idContract)
            throws ClientNotFoundException {
        contractService.blockByClient(idContract);
        return "redirect:/account/{idClient}";
    }

    @PostMapping("unblockContract/{idClient}/{idContract}")
    public String unblockContract(@PathVariable(value = "idClient") Long idClient,
                                @PathVariable(value = "idContract") Long idContract)
            throws ClientNotFoundException {
        contractService.unblockByClient(idContract);
        return "redirect:/account/{idClient}";
    }

    @PostMapping("updateTariff/{idClient}/{idContract}")
    public String unblockContract(@PathVariable(value = "idClient") Long idClient,
                                  @PathVariable(value = "idContract") Long idContract,
                                  @RequestParam(value = "tariffUpdated", required = false) String idTariff)
            throws ClientNotFoundException, TariffNotFoundException {
        contractService.updateTariff(idContract, idTariff);
        return "redirect:/account/{idClient}";
    }

    @PostMapping("updateOptions/{idClient}/{idContract}")
    public String updateOptions(
            @PathVariable(value = "idClient") Long idClient,
            @PathVariable(value = "idContract") Long idContract,
            Model model,
            @RequestParam(value = "optionsUpdated", required = false) String[] options)
            throws ClientNotFoundException, OptionNotFoundException {
        try {
            contractService.updateContract(idContract, options);
            return "redirect:/account/{idClient}";
        } catch (NotValidOptionsException e) {
            model.addAttribute("client", clientService.findClientByIdClient(idClient));
            model.addAttribute("tariffs", tariffService.getTariffs());
            model.addAttribute("error", e.getMessage());
            return "account";
        }

    }

}