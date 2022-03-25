package javaschool.ecare.loader;


import javaschool.ecare.exceptions.TariffAlreadyExistsException;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface LoaderService {

    void sendMessage() throws IOException, TimeoutException, TariffAlreadyExistsException;
}
