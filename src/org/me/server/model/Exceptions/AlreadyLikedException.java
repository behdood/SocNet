package org.me.server.model.Exceptions;


public class AlreadyLikedException extends BaseException {
    public AlreadyLikedException(String username) {
        super(username + " has liked this message before!");
    }
}
