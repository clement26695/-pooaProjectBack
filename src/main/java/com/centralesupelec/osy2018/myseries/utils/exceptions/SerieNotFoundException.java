package com.centralesupelec.osy2018.myseries.utils.exceptions;

public class SerieNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -9130937950207398867L;

    public SerieNotFoundException() {
		super("No serie was found with this search");
    }

    public SerieNotFoundException(String msg) {
		super(msg);
	}

	public SerieNotFoundException(String msg, Throwable t) {
		super(msg, t);
	}
}
