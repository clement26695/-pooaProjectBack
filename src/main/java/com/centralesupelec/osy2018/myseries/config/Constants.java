package com.centralesupelec.osy2018.myseries.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String ANONYMOUS_USER = "anonymoususer";
    public static final String DEFAULT_LANGUAGE = "en";

    public static final String JWT_SECRET_KEY = "NzU1NjI4N2M0ODY0NDIwYjg2MjU1OTI1M2MxMjZmODFmNGQ0N2I3ZTFkZjU4NTlhNjBhZjkxMmM1ZDg5YzYxZDZkMDNkNzY4ZjM4MDYyYWJjNDFkMGQwOTQ2M2YxZGNlM2JhMThlMzczNzZkODFiZTAzMGFmZjMwMjg2NTQ2YTY=";

	public static final long TokenValidityInSeconds = 86400;
    
    private Constants() {
    }
}
