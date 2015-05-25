package org.me.server.model.Exceptions;


public class UsernameAlreadyExistsException extends BaseException {
    public UsernameAlreadyExistsException() {
        super("Username Already Exists!");
    }
}
