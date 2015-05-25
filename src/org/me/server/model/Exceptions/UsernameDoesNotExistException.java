package org.me.server.model.Exceptions;


public class UsernameDoesNotExistException extends BaseException {
    public UsernameDoesNotExistException() {
        super("The Given Username Does Not Exist!");
    }
    public UsernameDoesNotExistException(String username) {
        super("The Username" + username + "Does Not Exist!");
    }
}
