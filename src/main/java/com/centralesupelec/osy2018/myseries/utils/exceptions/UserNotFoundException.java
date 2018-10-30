package com.centralesupelec.osy2018.myseries.utils.exceptions;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -9130937950207398867L;

    public UserNotFoundException(String msg) {
		super(msg);
	}

	public UserNotFoundException(String msg, Throwable t) {
		super(msg, t);
	}
}
