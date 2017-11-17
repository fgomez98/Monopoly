package com.company;

public class InvalidPaymentException extends RuntimeException {

    public InvalidPaymentException(String message) {
        super(message);
    }
    public InvalidPaymentException() { }
}
