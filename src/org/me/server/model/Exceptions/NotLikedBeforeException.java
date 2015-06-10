package org.me.server.model.Exceptions;


public class NotLikedBeforeException extends BaseException {
    public NotLikedBeforeException(String username) {
        super(username + " has not previously liked this message!");
    }
    public NotLikedBeforeException() {
        super("This feed has not previously been liked!");
    }
}
