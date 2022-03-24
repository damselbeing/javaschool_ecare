package javaschool.ecare.controllers;

import javaschool.ecare.exceptions.TariffAlreadyExistsException;
import javaschool.ecare.services.api.LoaderService;
import javaschool.ecare.services.impl.LoaderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Controller
@RequestMapping(path = "/")
public class LoaderController {

    private final LoaderService loaderService;

    @Autowired
    public LoaderController(
            LoaderServiceImpl loaderService) {
        this.loaderService = loaderService;

    }

    @GetMapping("loader")
    public String loadMessage () throws IOException, TimeoutException, TariffAlreadyExistsException {
        loaderService.loadMessage();
        return "loader";
    }



}