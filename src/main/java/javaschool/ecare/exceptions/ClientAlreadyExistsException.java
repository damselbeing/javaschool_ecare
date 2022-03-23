package javaschool.ecare.exceptions;

public class ClientAlreadyExistsException extends Exception{

    public ClientAlreadyExistsException() {
        super("Client with this email or passport already exists!");
    }
}
