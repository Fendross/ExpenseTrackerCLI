package main.java.exceptions;

public class DeleteException extends Exception {
    public DeleteException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
