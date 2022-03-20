package javaschool.ecare.exceptions;

public class ContractNotFoundException extends Exception{
    public ContractNotFoundException() {
        super("No client with this contract exists!");
    }
}
