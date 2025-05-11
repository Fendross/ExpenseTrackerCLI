package exceptions;

public class AddException extends Exception {
    public AddException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
