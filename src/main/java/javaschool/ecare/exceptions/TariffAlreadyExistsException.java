package javaschool.ecare.exceptions;

public class TariffAlreadyExistsException extends Exception{

    public TariffAlreadyExistsException() {

        super("This tariff name is taken!");
    }
}
