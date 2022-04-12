package javaschool.ecare.controllers;

import javaschool.ecare.dto.ClientDto;
import javaschool.ecare.exceptions.ClientNotFoundException;
import javaschool.ecare.services.api.ClientService;
import javaschool.ecare.services.api.ContractService;
import javaschool.ecare.services.api.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/admin/")
public class TariffController {


}