package com.fendross.expensetrackercli.exceptions;

public class DeleteException extends Exception {
    public DeleteException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
