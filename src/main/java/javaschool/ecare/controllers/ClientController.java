package javaschool.ecare.controllers;

import javaschool.ecare.exceptions.*;
import javaschool.ecare.services.api.ClientService;
import javaschool.ecare.services.api.ContractService;
import javaschool.ecare.services.api.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.concurrent.TimeoutException;

@Controller
@RequestMapping(path = "/client/")
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


    @GetMapping("account")
    public String showPersonalAccount(
            Principal principal,
            Model model
    ) throws ClientNotFoundException {
        String email = principal.getName();
        model.addAttribute("client", clientService.findClientByEmail(email));
        model.addAttribute("tariffs", tariffService.getTariffs());
        return "client/account";
    }


    @PostMapping("blockContract/{idClient}/{idContract}")
    public String blockContract(@PathVariable(value = "idClient") Long idClient,
                                @PathVariable(value = "idContract") Long idContract)
            throws ContractNotFoundException {
        contractService.blockByClient(idContract);
        return "redirect:/client/account/";
    }

    @PostMapping("unblockContract/{idClient}/{idContract}")
    public String unblockContract(@PathVariable(value = "idClient") Long idClient,
                                @PathVariable(value = "idContract") Long idContract)
            throws ContractNotFoundException {
        contractService.unblockByClient(idContract);
        return "redirect:/client/account/";
    }

    @PostMapping("updateTariff/{idClient}/{idContract}")
    public String updateTariff(@PathVariable(value = "idClient") Long idClient,
                                  @PathVariable(value = "idContract") Long idContract,
                                  @RequestParam(value = "tariffUpdated", required = false) String idTariff)
            throws ContractNotFoundException, TariffNotFoundException, IOException, TimeoutException {
        contractService.updateContractTariff(idContract, idTariff);
        return "redirect:/client/account/";
    }

    @PostMapping("updateOptions/{idClient}/{idContract}")
    public String updateOptions(
            @PathVariable(value = "idClient") Long idClient,
            @PathVariable(value = "idContract") Long idContract,
            Model model,
            @RequestParam(value = "optionsUpdated", required = false) String[] options)
            throws ClientNotFoundException, OptionNotFoundException, ContractNotFoundException {
        try {
            contractService.updateContractOptions(idContract, options);
            return "redirect:/client/account/";
        } catch (NotValidOptionsException e) {
            model.addAttribute("client", clientService.findClientByIdClient(idClient));
            model.addAttribute("tariffs", tariffService.getTariffs());
            model.addAttribute("error", e.getMessage());
            return "client/account";
        }

    }

}