package com.unistagram.userapp.exception;

public class ObjectIdException extends RuntimeException {
    public ObjectIdException(String message) {
        super(message);
    }

    public ObjectIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
