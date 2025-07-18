package com.abdul.email.domain.email;

public class UnableToSendEmailException extends RuntimeException {

    public UnableToSendEmailException(String message) {
        super(message);
    }

    public UnableToSendEmailException() {
        super("Unable to send email");
    }
}
