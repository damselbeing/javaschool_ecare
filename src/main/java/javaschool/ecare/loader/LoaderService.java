package javaschool.ecare.loader;


import javaschool.ecare.exceptions.TariffNotFoundException;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface LoaderService {

    void sendMessage() throws IOException, TimeoutException, TariffNotFoundException;
}
