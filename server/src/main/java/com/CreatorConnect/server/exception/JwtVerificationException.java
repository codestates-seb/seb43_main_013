package com.CreatorConnect.server.exception;

public class JwtVerificationException extends RuntimeException {

    public JwtVerificationException(String message, Throwable cause) {
        super(message, cause);
    }
}
