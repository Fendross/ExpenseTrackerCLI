package main.java.exceptions;

public class ReportException extends Exception {
    public ReportException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
