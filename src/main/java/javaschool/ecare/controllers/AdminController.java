package javaschool.ecare.controllers;

import javaschool.ecare.dto.ClientDto;
import javaschool.ecare.dto.TariffDto;
import javaschool.ecare.exceptions.ClientNotFoundException;
import javaschool.ecare.exceptions.TariffNotFoundException;
import javaschool.ecare.services.api.ClientService;
import javaschool.ecare.services.api.ContractService;
import javaschool.ecare.services.api.OptionService;
import javaschool.ecare.services.api.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/admin/")
public class AdminController {

    private final ClientService clientService;
    private final ContractService contractService;
    private final TariffService tariffService;
    private final OptionService optionService;

    @Autowired
    public AdminController(ClientService clientService, ContractService contractService, TariffService tariffService, OptionService optionService) {

        this.clientService = clientService;
        this.contractService = contractService;
        this.tariffService = tariffService;
        this.optionService = optionService;
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

    @PostMapping("blockContract/{id}")
    public String blockContract(@PathVariable(value = "id") Long id) throws ClientNotFoundException {
        contractService.blockByAdmin(id);
        return "redirect:/admin/contractProfile/{id}";
    }

    @PostMapping("unblockContract/{id}")
    public String unblockContract(@PathVariable(value = "id") Long id) throws ClientNotFoundException {
        contractService.unblockByAdmin(id);
        return "redirect:/admin/contractProfile/{id}";
    }

    @GetMapping("tariffs")
    public String viewTariffs(Model model) {
        model.addAttribute("tariffs", tariffService.getTariffs());
        return "admin/view-tariffs";
    }

    @PostMapping("archiveTariff/{id}")
    public String archiveTariff(@PathVariable(value = "id") Long id) throws TariffNotFoundException {
        tariffService.archive(id);
        return "redirect:/admin/tariffs";
    }

    @GetMapping("updateTariff/{id}")
    public String updateTariff(@PathVariable(value = "id") Long id, Model model) throws TariffNotFoundException {
        model.addAttribute("tariff", tariffService.findTariffByIdTariff(id));
        model.addAttribute("options", optionService.getOptions());
        return "admin/tariff-profile";
    }

    @GetMapping("options")
    public String viewOptions(Model model) {
        model.addAttribute("options", optionService.getOptions());
        return "admin/view-options";
    }

}