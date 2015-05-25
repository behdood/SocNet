package org.me.server.model.Exceptions;


public class AlreadyFollowingException extends Exception {
    public AlreadyFollowingException(String user1, String user2) {
        super(user1 + " already follows " + user2);
    }
}
