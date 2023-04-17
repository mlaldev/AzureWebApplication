package com.azure.server.CustomExceptions;

import org.springframework.security.authentication.BadCredentialsException;

public class ApplicationBadCredentialsException extends BadCredentialsException {
    public ApplicationBadCredentialsException(String msg) {
        super(msg);
    }
}
