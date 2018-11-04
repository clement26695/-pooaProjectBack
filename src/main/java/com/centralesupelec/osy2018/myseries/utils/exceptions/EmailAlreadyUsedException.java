package com.centralesupelec.osy2018.myseries.utils.exceptions;

public class EmailAlreadyUsedException extends Exception {

    private static final long serialVersionUID = 1L;

    public EmailAlreadyUsedException() {
        super("Email already used!");
    }
}
