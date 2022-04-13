package javaschool.ecare.controllers;

import javaschool.ecare.exceptions.*;
import javaschool.ecare.services.api.ClientService;
import javaschool.ecare.services.api.ContractService;
import javaschool.ecare.services.api.TariffService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

@Log4j2
@Controller
@RequestMapping(path = "/client/")
public class ClientController {

    private final String redirect = "redirect:/client/account/";

    private final ClientService clientService;
    private final ContractService contractService;
    private final TariffService tariffService;

    @Autowired
    public ClientController(ClientService clientService,
                            ContractService contractService,
                            TariffService tariffService) {
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
        log.warn("Contract has been blocked by Client: "
                + contractService.findContractByIdContract(idContract).getNumber());
        return redirect;
    }

    @PostMapping("unblockContract/{idClient}/{idContract}")
    public String unblockContract(@PathVariable(value = "idClient") Long idClient,
                                @PathVariable(value = "idContract") Long idContract)
            throws ContractNotFoundException {
        contractService.unblockByClient(idContract);
        log.warn("Contract has been unblocked by Client: "
                + contractService.findContractByIdContract(idContract).getNumber());
        return redirect;
    }

    @PostMapping("updateTariff/{idClient}/{idContract}")
    public String updateTariff(@PathVariable(value = "idClient") Long idClient,
                                  @PathVariable(value = "idContract") Long idContract,
                                  @RequestParam(value = "tariffUpdated", required = false) String idTariff)
            throws ContractNotFoundException, TariffNotFoundException, IOException, TimeoutException {
        contractService.updateContractTariff(idContract, idTariff);
        log.info("Tariff of Contract " + contractService.findContractByIdContract(idContract).getNumber()
                + " has been updated to: " + tariffService.findTariffByIdTariff(Long.parseLong(idTariff)).getName());
        return redirect;
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
            log.info("Options of Contract " + contractService.findContractByIdContract(idContract).getNumber()
                    + " have been updated to: " + Arrays.toString(options));
            return redirect;
        } catch (NotValidOptionsException e) {
            model.addAttribute("client", clientService.findClientByIdClient(idClient));
            model.addAttribute("tariffs", tariffService.getTariffs());
            model.addAttribute("error", e.getMessage());
            log.error("Error! Chosen options are not valid for update: " + Arrays.toString(options));
            return "client/account";
        }

    }

}