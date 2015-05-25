package org.me.server.model.Exceptions;


public class PostDoesNotExistException extends BaseException {
    public PostDoesNotExistException() {
        super("This post does not exist!");
    }
}
