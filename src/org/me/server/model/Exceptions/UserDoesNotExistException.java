package org.me.server.model.Exceptions;


public class UserDoesNotExistException extends BaseException {
    public UserDoesNotExistException() {
        super("The Given Username Does Not Exist!");
    }
    public UserDoesNotExistException(String username) {
        super("The Username" + username + "Does Not Exist!");
    }
}
