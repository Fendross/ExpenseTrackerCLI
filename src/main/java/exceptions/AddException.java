package main.java.exceptions;

public class AddException extends Exception {
    public AddException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
