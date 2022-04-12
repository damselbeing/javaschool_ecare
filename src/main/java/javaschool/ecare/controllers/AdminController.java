package javaschool.ecare.controllers;

import javaschool.ecare.dto.ClientDto;
import javaschool.ecare.dto.ContractDto;
import javaschool.ecare.dto.TariffDto;
import javaschool.ecare.entities.Client;
import javaschool.ecare.exceptions.*;
import javaschool.ecare.services.api.ClientService;
import javaschool.ecare.services.api.ContractService;
import javaschool.ecare.services.api.OptionService;
import javaschool.ecare.services.api.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

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
    public String viewClients(
            @RequestParam(required = false, name = "contractNumber") String contractNumber,
            Model model
    ) throws ClientNotFoundException {
        if(contractNumber == null) {
            model.addAttribute("clients", clientService.getClients());//NOSONAR
        } else {
            try{
                List<ClientDto> listOfOne = new ArrayList<>();
                listOfOne.add(clientService.findClientByContract(contractNumber));
                model.addAttribute("clients", listOfOne);//NOSONAR
            } catch (ContractNotFoundException e) {
                model.addAttribute("clients", clientService.getClients());//NOSONAR
                model.addAttribute("error", e.getMessage());//NOSONAR
            }

        }
        return "admin/view-clients";
    }





    @GetMapping("contractProfile/{idClient}")
    public String showContractProfile(
            @PathVariable(value = "idClient") Long idClient,
            Model model
    ) throws ClientNotFoundException {
        model.addAttribute("client", clientService.findClientByIdClient(idClient));//NOSONAR
        model.addAttribute("tariffs", tariffService.getTariffs());//NOSONAR
        return "admin/contract-profile";
    }


    @PostMapping("blockContract/{idClient}/{idContract}")
    public String blockContract(
            @PathVariable(value = "idClient") Long idClient,
            @PathVariable(value = "idContract") Long idContract
            ) throws ContractNotFoundException {
        contractService.blockByAdmin(idContract);
        return "redirect:/admin/contractProfile/{idClient}";//NOSONAR
    }

    @PostMapping("unblockContract/{idClient}/{idContract}")
    public String unblockContract(
            @PathVariable(value = "idClient") Long idClient,
            @PathVariable(value = "idContract") Long idContract
    ) throws ContractNotFoundException {
        contractService.unblockByAdmin(idContract);
        return "redirect:/admin/contractProfile/{idClient}";//NOSONAR
    }

    @GetMapping("tariffs")
    public String viewTariffs(Model model) {
        model.addAttribute("tariffs", tariffService.getTariffs());//NOSONAR
        TariffDto dto = new TariffDto();
        model.addAttribute("newTariff", dto);
        return "admin/view-tariffs";
    }

    @PostMapping("addTariff")
    public String addNewTariff(@ModelAttribute("newTariff") TariffDto dto, Model model) {
        try{
            tariffService.addNewTariff(dto);
            return "redirect:/admin/tariffs";
        } catch(TariffAlreadyExistsException e) {
            model.addAttribute("tariffs", tariffService.getTariffs());//NOSONAR
            TariffDto dtoNew = new TariffDto();
            model.addAttribute("newTariff", dtoNew);
            model.addAttribute("error", e.getMessage());//NOSONAR
            return "admin/view-tariffs";
        }

    }


    @PostMapping("archiveTariff/{id}")
    public String archiveTariff(@PathVariable(value = "id") Long id) throws TariffNotFoundException {
        tariffService.archiveTariff(id);
        return "redirect:/admin/tariffs";
    }

    @GetMapping("tariffProfile/{id}")
    public String showTariffProfile(@PathVariable(value = "id") Long id, Model model) throws TariffNotFoundException {
        model.addAttribute("tariff", tariffService.findTariffByIdTariff(id));
        model.addAttribute("optionsTotal", optionService.getOptions());//NOSONAR
        return "admin/tariff-profile";
    }

    @PostMapping("updateTariff/{id}")
    public String updateTariff(
            @PathVariable(value = "id") Long id,
            Model model,
            @RequestParam(value = "options", required = false) String[] options
    ) throws TariffNotFoundException, OptionNotFoundException {
        try {
            tariffService.updateTariffOptions(id, options);
            return "redirect:/admin/tariffProfile/{id}";
        } catch (NotValidOptionsException e) {
            model.addAttribute("tariff", tariffService.findTariffByIdTariff(id));
            model.addAttribute("optionsTotal", optionService.getOptions());//NOSONAR
            model.addAttribute("error", e.getMessage());//NOSONAR
            return "admin/tariff-profile";
        }

    }

    @GetMapping("options")
    public String viewOptions(Model model) {
        model.addAttribute("options", optionService.getOptions());
        model.addAttribute("contracts", contractService.getContracts());
        return "admin/view-options";
    }

    @GetMapping("optionProfile/{id}")
    public String showOptionProfile(@PathVariable(value = "id") Long id, Model model) throws OptionNotFoundException {
        model.addAttribute("option", optionService.findOptionByIdOption(id));
        model.addAttribute("optionsTotal", optionService.getOptions());//NOSONAR
        return "admin/option-profile";
    }


    @PostMapping("updateOption/{id}")
    public String updateOption(
            @PathVariable(value = "id") Long id,
            Model model,
            @RequestParam(value = "optionsAdditional", required = false) String[] optionsAdditional,
            @RequestParam(value = "optionsConflicting", required = false) String[] optionsConflicting
    ) throws OptionNotFoundException {
        try {
            optionService.updateOption(id, optionsAdditional, optionsConflicting);
            return "redirect:/admin/optionProfile/{id}";
        } catch (NotValidOptionsException e) {
            model.addAttribute("option", optionService.findOptionByIdOption(id));
            model.addAttribute("optionsTotal", optionService.getOptions());//NOSONAR
            model.addAttribute("error", e.getMessage());//NOSONAR
            return "admin/option-profile";
        }

    }

    @PostMapping("updateOptions/{idClient}/{idContract}")
    public String updateContractOptions(
            @PathVariable(value = "idClient") Long idClient,
            @PathVariable(value = "idContract") Long idContract,
            Model model,
            @RequestParam(value = "optionsUpdated", required = false) String[] options)
            throws ContractNotFoundException, OptionNotFoundException, ClientNotFoundException {
        try {
            contractService.updateContractOptions(idContract, options);
            return "redirect:/admin/contractProfile/{idClient}";//NOSONAR
        } catch (NotValidOptionsException e) {
            model.addAttribute("client", clientService.findClientByIdClient(idClient));//NOSONAR
            model.addAttribute("tariffs", tariffService.getTariffs());//NOSONAR
            model.addAttribute("error", e.getMessage());//NOSONAR
            return "admin/contract-profile";
        }

    }

    @PostMapping("updateTariff/{idClient}/{idContract}")
    public String updateContractTariff(@PathVariable(value = "idClient") Long idClient,
                               @PathVariable(value = "idContract") Long idContract,
                               @RequestParam(value = "tariffUpdated", required = false) String idTariff)
            throws ContractNotFoundException, TariffNotFoundException, IOException, TimeoutException {
        contractService.updateContractTariff(idContract, idTariff);
        return "redirect:/admin/contractProfile/{idClient}";//NOSONAR
    }


    @GetMapping("addContract/{idClient}")
    public String draftNewContract(
            @PathVariable(value = "idClient") Long idClient,
            Model model
    ) throws ClientNotFoundException {
        model.addAttribute("client", clientService.findClientByIdClient(idClient));//NOSONAR
        model.addAttribute("telnumbers", contractService.getGeneratedNumbers());
        ContractDto dto = new ContractDto();
        model.addAttribute("number", dto);
        return "admin/view-numbers";
    }

    @PostMapping("saveContract/{idClient}")
    public String addNewContract(
            @PathVariable(value = "idClient") Long idClient,
            @ModelAttribute("number") ContractDto dto
    ) throws ClientNotFoundException, ContractNotFoundException {
        contractService.addNewContract(dto, idClient);
        return "redirect:/admin/contractProfile/{idClient}";//NOSONAR
    }



}