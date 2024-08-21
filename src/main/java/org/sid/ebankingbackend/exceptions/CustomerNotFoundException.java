package org.sid.ebankingbackend.exceptions;

// RuntimeException is a subclass of Exception (exception not servier)
public class CustomerNotFoundException extends Exception {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
