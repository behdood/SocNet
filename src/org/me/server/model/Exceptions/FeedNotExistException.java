package org.me.server.model.Exceptions;


public class FeedNotExistException extends BaseException {
    public FeedNotExistException() {
        super("This post does not exist!");
    }
}
