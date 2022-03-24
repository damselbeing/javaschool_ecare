package javaschool.ecare.services.api;


import javaschool.ecare.exceptions.TariffAlreadyExistsException;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface LoaderService {

    void loadMessage() throws IOException, TimeoutException, TariffAlreadyExistsException;

}
