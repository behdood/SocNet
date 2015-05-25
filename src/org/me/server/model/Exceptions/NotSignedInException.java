package org.me.server.model.Exceptions;


public class NotSignedInException extends BaseException {
    public NotSignedInException() {
        super("You have to sign in first!");
    }
}
