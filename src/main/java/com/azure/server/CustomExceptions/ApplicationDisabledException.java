package com.azure.server.CustomExceptions;

import org.springframework.security.authentication.DisabledException;

public class ApplicationDisabledException extends DisabledException {
    public ApplicationDisabledException(String msg) {
        super(msg);
    }
}
