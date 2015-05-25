package org.me.server.model.Exceptions;


public class IncorrectPasswordException extends BaseException {
    public IncorrectPasswordException() {
        super("Password Is Incorrect!");
    }
}
