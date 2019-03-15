package com.henlf.orm.exception;

public class UnknownException extends RuntimeException {
    public UnknownException() {
        super();
    }

    public UnknownException(String message) {
        super(message);
    }

    public UnknownException(String message, Throwable cause) {
        super(message, cause);
    }
}
