package javaschool.ecare.controllers;

import javaschool.ecare.dto.ClientDto;
import javaschool.ecare.services.api.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

//    @GetMapping("welcome")
//    public String showMainPage() {
//        return "welcome";
//    }

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

        if (!dto.getPassword().equals(dto.getPasswordConfirm())){
            model.addAttribute("passwordError", "Password confirmation failed!");
            return "registration";
        }
        if (!clientService.saveClient(dto)){
            model.addAttribute("usernameError", "User with this email already exists!");
            return "registration";
        }

        return "redirect:/login";


    }


}