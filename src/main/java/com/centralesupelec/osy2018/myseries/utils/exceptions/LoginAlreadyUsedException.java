package com.centralesupelec.osy2018.myseries.utils.exceptions;

public class LoginAlreadyUsedException extends Exception {

    private static final long serialVersionUID = 1L;

    public LoginAlreadyUsedException() {
        super("Login name already used!");
    }
}
