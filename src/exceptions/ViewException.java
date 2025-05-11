package exceptions;

public class ViewException extends Exception {
    public ViewException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
