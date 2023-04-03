package com.unistagram.exception;

public class ParameterErrorNumberException extends RuntimeException {
    public ParameterErrorNumberException(String message) {
        super(message);
    }

    public ParameterErrorNumberException(String message, Throwable cause) {
        super(message, cause);
    }
}
