package com.azure.server.CustomExceptions;

public class UserFoundException extends Exception{
    String msg;

    public UserFoundException() {
        super("Username already exist.");
    }

    public UserFoundException(String msg) {
        super(msg);
    }
}
