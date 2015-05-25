package org.me.server.model.Exceptions;


public class NotFollowingException extends BaseException {
    public NotFollowingException(String user1, String user2) {
        super(user1 + " currently does not follow " + user2);
    }
}
