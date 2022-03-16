package javaschool.ecare.exceptions;

public class NotValidOptionsException extends Exception{

    public NotValidOptionsException() {
        super("Please note additional and conflicting options while editing! " +
                "All the additional options of the selected option must be added as well! " +
                "Additional and conflicting options cannot be added together!");
    }
}
