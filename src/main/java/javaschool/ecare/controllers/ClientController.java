package javaschool.ecare.controllers;

import javaschool.ecare.dto.ClientDto;
import javaschool.ecare.exceptions.ClientNotFoundException;
import javaschool.ecare.exceptions.NotValidOptionsException;
import javaschool.ecare.exceptions.OptionNotFoundException;
import javaschool.ecare.exceptions.TariffNotFoundException;
import javaschool.ecare.repositories.ClientRepository;
import javaschool.ecare.repositories.TariffRepository;
import javaschool.ecare.services.api.ClientService;
import javaschool.ecare.services.api.ContractService;
import javaschool.ecare.services.api.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping(path = "/client/")
public class ClientController {

    private final ClientService clientService;
    private final ContractService contractService;
    private final ClientRepository clientRepository;
    private final TariffService tariffService;

    @Autowired
    public ClientController(ClientService clientService, ContractService contractService, ClientRepository clientRepository, TariffService tariffService) {
        this.clientService = clientService;
        this.contractService = contractService;
        this.tariffService = tariffService;
        this.clientRepository = clientRepository;
    }

//    @Controller
//    public class SecurityController {
//
//        @RequestMapping(value = "/client", method = RequestMethod.GET)
//        @ResponseBody
//        public String currentUserName(Principal principal) {
//
//            System.out.println(principal.getName());
//            String email = principal.getName();
//            return email;
//        }
//    }

//    @GetMapping("account/{id}")
//    public String showPersonalAccount(
//            @PathVariable(value = "id") Long id,
//            Model model
//    ) throws ClientNotFoundException {
//        model.addAttribute("client", clientService.findClientByIdClient(id));
//        model.addAttribute("tariffs", tariffService.getTariffs());
//        return "client/account";
//    }



    @GetMapping("account")
    public String showPersonalAccount(
            Principal principal,
            Model model
    ) throws ClientNotFoundException {
        String email = principal.getName();
        model.addAttribute("client", clientRepository.findClientByEmail(email).orElseThrow(ClientNotFoundException::new));
        model.addAttribute("tariffs", tariffService.getTariffs());
        return "client/account";
    }

//    @GetMapping("account")
//    public String showPersonalAccount(){
//
//        return "client/account";
//    }

    @PostMapping("blockContract/{idClient}/{idContract}")
    public String blockContract(@PathVariable(value = "idClient") Long idClient,
                                @PathVariable(value = "idContract") Long idContract)
            throws ClientNotFoundException {
        contractService.blockByClient(idContract);
        return "redirect:/client/account/";
    }

    @PostMapping("unblockContract/{idClient}/{idContract}")
    public String unblockContract(@PathVariable(value = "idClient") Long idClient,
                                @PathVariable(value = "idContract") Long idContract)
            throws ClientNotFoundException {
        contractService.unblockByClient(idContract);
        return "redirect:/client/account/";
    }

    @PostMapping("updateTariff/{idClient}/{idContract}")
    public String updateTariff(@PathVariable(value = "idClient") Long idClient,
                                  @PathVariable(value = "idContract") Long idContract,
                                  @RequestParam(value = "tariffUpdated", required = false) String idTariff)
            throws ClientNotFoundException, TariffNotFoundException {
        contractService.updateTariff(idContract, idTariff);
        return "redirect:/client/account/";
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
            return "redirect:/client/account/";
        } catch (NotValidOptionsException e) {
            model.addAttribute("client", clientService.findClientByIdClient(idClient));
            model.addAttribute("tariffs", tariffService.getTariffs());
            model.addAttribute("error", e.getMessage());
            return "client/account";
        }

    }

}