package com.epam.jwd.exception;

public class EmptyCartException extends Exception{
    public EmptyCartException() {
        super();
    }

    public EmptyCartException(String message) {
        super(message);
    }
}
