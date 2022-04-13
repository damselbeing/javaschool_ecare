package javaschool.ecare.controllers;

import javaschool.ecare.dto.ClientDto;
import javaschool.ecare.exceptions.ClientAlreadyExistsException;
import javaschool.ecare.exceptions.PasswordConfirmationFailedException;
import javaschool.ecare.services.api.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/")
public class RegistrationController {

    private final ClientService clientService;

    @Autowired
    public RegistrationController(
            ClientService clientService) {
        this.clientService = clientService;

    }


    @GetMapping("registration")
    public String showRegistrationForm(Model model) {
        ClientDto dto = new ClientDto();
        model.addAttribute("client", dto);
        return "registration";
    }

    @PostMapping("registration")
    public String registerNewClient(
            @ModelAttribute("client") ClientDto dto,
            Model model) {

        try {
            clientService.registerNewClient(dto);
            return "redirect:/login";
        } catch (ClientAlreadyExistsException | PasswordConfirmationFailedException e) {
            model.addAttribute("error", e.getMessage());
            return "registration";
        }
    }


}