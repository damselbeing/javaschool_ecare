package javaschool.ecare.controllers;

import javaschool.ecare.dto.ClientDto;
import javaschool.ecare.dto.ContractDto;
import javaschool.ecare.dto.TariffDto;
import javaschool.ecare.exceptions.*;
import javaschool.ecare.services.api.ClientService;
import javaschool.ecare.services.api.ContractService;
import javaschool.ecare.services.api.OptionService;
import javaschool.ecare.services.api.TariffService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Log4j2
@Controller
@RequestMapping(path = "/admin/")
public class AdminController {

    private final String notValidOptionsError = "Error! Chosen options are not valid for update: ";
    private final String clientsForModel = "clients";
    private final String clientForModel = "client";
    private final String tariffsForModel = "tariffs";
    private final String optionsTotalForModel = "optionsTotal";
    private final String errorForModel = "error";
    private final String redirect = "redirect:/admin/contractProfile/{idClient}";

    private final ClientService clientService;
    private final ContractService contractService;
    private final TariffService tariffService;
    private final OptionService optionService;

    @Autowired
    public AdminController(ClientService clientService,
                           ContractService contractService,
                           TariffService tariffService,
                           OptionService optionService) {
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
            model.addAttribute(clientsForModel, clientService.getClients());
        } else {
            try{
                List<ClientDto> listOfOne = new ArrayList<>();
                listOfOne.add(clientService.findClientByContract(contractNumber));
                model.addAttribute(clientsForModel, listOfOne);
            } catch (ContractNotFoundException e) {
                model.addAttribute(clientsForModel, clientService.getClients());
                model.addAttribute(errorForModel, e.getMessage());
                log.error("Error! Client with this number does not exist: " + contractNumber);
            }

        }
        return "admin/view-clients";
    }


    @GetMapping("contractProfile/{idClient}")
    public String showContractProfile(
            @PathVariable(value = "idClient") Long idClient,
            Model model
    ) throws ClientNotFoundException {
        model.addAttribute(clientForModel, clientService.findClientByIdClient(idClient));
        model.addAttribute(tariffsForModel, tariffService.getTariffs());
        return "admin/contract-profile";
    }


    @PostMapping("blockContract/{idClient}/{idContract}")
    public String blockContract(
            @PathVariable(value = "idClient") Long idClient,
            @PathVariable(value = "idContract") Long idContract
            ) throws ContractNotFoundException {
        contractService.blockByAdmin(idContract);
        log.warn("Contract has been blocked by Admin: "
                + contractService.findContractByIdContract(idContract).getNumber());
        return redirect;
    }

    @PostMapping("unblockContract/{idClient}/{idContract}")
    public String unblockContract(
            @PathVariable(value = "idClient") Long idClient,
            @PathVariable(value = "idContract") Long idContract
    ) throws ContractNotFoundException {
        contractService.unblockByAdmin(idContract);
        log.warn("Contract has been unblocked by Admin: "
                + contractService.findContractByIdContract(idContract).getNumber());
        return redirect;
    }

    @GetMapping("tariffs")
    public String viewTariffs(Model model) {
        model.addAttribute(tariffsForModel, tariffService.getTariffs());
        TariffDto dto = new TariffDto();
        model.addAttribute("newTariff", dto);
        return "admin/view-tariffs";
    }

    @PostMapping("addTariff")
    public String addNewTariff(@ModelAttribute("newTariff") TariffDto dto, Model model) {
        try{
            tariffService.addNewTariff(dto);
            log.info("A new tariff has been created: " + dto.getName());
            return "redirect:/admin/tariffs";
        } catch(TariffAlreadyExistsException e) {
            model.addAttribute(tariffsForModel, tariffService.getTariffs());
            TariffDto dtoNew = new TariffDto();
            model.addAttribute("newTariff", dtoNew);
            model.addAttribute(errorForModel, e.getMessage());
            log.error("Error! Tariff with this name already exists: " + dto.getName());
            return "admin/view-tariffs";
        }

    }

    @PostMapping("archiveTariff/{id}")
    public String archiveTariff(@PathVariable(value = "id") Long id)
            throws TariffNotFoundException {
        tariffService.archiveTariff(id);
        TariffDto tariff = tariffService.findTariffByIdTariff(id);
        log.warn("Tariff has been marked for deletion: " + tariff.getName());
        return "redirect:/admin/tariffs";
    }

    @GetMapping("tariffProfile/{id}")
    public String showTariffProfile(@PathVariable(value = "id") Long id, Model model)
            throws TariffNotFoundException {
        model.addAttribute("tariff", tariffService.findTariffByIdTariff(id));
        model.addAttribute(optionsTotalForModel, optionService.getOptions());
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
            log.info("Options of Tariff " + tariffService.findTariffByIdTariff(id).getName()
                    + " have been updated to: " + Arrays.toString(options));
            return "redirect:/admin/tariffProfile/{id}";
        } catch (NotValidOptionsException e) {
            model.addAttribute("tariff", tariffService.findTariffByIdTariff(id));
            model.addAttribute(optionsTotalForModel, optionService.getOptions());
            model.addAttribute(errorForModel, e.getMessage());
            log.error(notValidOptionsError + Arrays.toString(options));
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
    public String showOptionProfile(@PathVariable(value = "id") Long id, Model model)
            throws OptionNotFoundException {
        model.addAttribute("option", optionService.findOptionByIdOption(id));
        model.addAttribute(optionsTotalForModel, optionService.getOptions());
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
            log.info("Option " + optionService.findOptionByIdOption(id).getName()
                    + " has been updated: add.opt. " + Arrays.toString(optionsAdditional)
                    + ", confl.opt. " + Arrays.toString(optionsConflicting));
            return "redirect:/admin/optionProfile/{id}";
        } catch (NotValidOptionsException e) {
            model.addAttribute("option", optionService.findOptionByIdOption(id));
            model.addAttribute(optionsTotalForModel, optionService.getOptions());
            model.addAttribute(errorForModel, e.getMessage());
            log.error(notValidOptionsError +
                    "add.opt. " + Arrays.toString(optionsAdditional) +
                    ", confl.opt. " + Arrays.toString(optionsConflicting));
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
            log.info("Options of Contract " + contractService.findContractByIdContract(idContract).getNumber()
                    + " have been updated to: " + Arrays.toString(options));
            return redirect;
        } catch (NotValidOptionsException e) {
            model.addAttribute(clientForModel, clientService.findClientByIdClient(idClient));
            model.addAttribute(tariffsForModel, tariffService.getTariffs());
            model.addAttribute(errorForModel, e.getMessage());
            log.error(notValidOptionsError + Arrays.toString(options));
            return "admin/contract-profile";
        }

    }

    @PostMapping("updateTariff/{idClient}/{idContract}")
    public String updateContractTariff(@PathVariable(value = "idClient") Long idClient,
                               @PathVariable(value = "idContract") Long idContract,
                               @RequestParam(value = "tariffUpdated", required = false) String idTariff)
            throws ContractNotFoundException, TariffNotFoundException, IOException, TimeoutException {
        contractService.updateContractTariff(idContract, idTariff);
        log.info("Tariff of Contract " + contractService.findContractByIdContract(idContract).getNumber()
                + " has been updated to: " + contractService.findContractByIdContract(idContract).getTariff().getName());
        return redirect;
    }


    @GetMapping("addContract/{idClient}")
    public String draftNewContract(
            @PathVariable(value = "idClient") Long idClient,
            Model model
    ) throws ClientNotFoundException {
        model.addAttribute(clientForModel, clientService.findClientByIdClient(idClient));
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
        log.info("A new contract has been created: " + dto.getNumber());
        return redirect;
    }



}