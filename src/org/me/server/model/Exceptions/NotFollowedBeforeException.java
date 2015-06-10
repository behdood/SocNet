package org.me.server.model.Exceptions;


public class NotFollowedBeforeException extends BaseException {
    public NotFollowedBeforeException(String user1, String user2) {
        super(user1 + " currently does not follow " + user2);
    }
}
