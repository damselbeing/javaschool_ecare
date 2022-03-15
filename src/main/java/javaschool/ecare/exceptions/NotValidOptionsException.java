package javaschool.ecare.exceptions;

public class NotValidOptionsException extends Exception{

    public NotValidOptionsException() {
        super("Please note additional and conflicting options while editing!");
    }
}
