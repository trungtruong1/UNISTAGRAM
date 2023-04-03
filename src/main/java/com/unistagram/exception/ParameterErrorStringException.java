package com.unistagram.exception;

public class ParameterErrorStringException extends RuntimeException {
    public ParameterErrorStringException(String message) {
        super(message);
    }

    public ParameterErrorStringException(String message, Throwable cause) {
        super(message, cause);
    }
}
