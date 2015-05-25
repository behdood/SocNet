package org.me.server.model.Exceptions;


public class InvalidPostIdException extends BaseException {
    public InvalidPostIdException() { super("Weird thing happened! post_id already exist!"); }
}
