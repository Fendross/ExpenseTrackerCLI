package com.fendross.expensetrackercli.exceptions;

public class FsException extends Exception {
    public FsException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
