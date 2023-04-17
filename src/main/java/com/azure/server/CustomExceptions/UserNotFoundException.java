package com.azure.server.CustomExceptions;

public class UserNotFoundException extends Exception{
    String msg;

    public UserNotFoundException() {
        super("User does not exist.");
    }

    public UserNotFoundException(String msg) {
        super(msg);
    }
}
