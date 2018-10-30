package com.centralesupelec.osy2018.myseries.utils.exceptions;

public class EpisodeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -9130937950207398867L;

    public EpisodeNotFoundException(String msg) {
		super(msg);
	}

	public EpisodeNotFoundException(String msg, Throwable t) {
		super(msg, t);
	}
}
