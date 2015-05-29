package org.me.server.model.dao;


import org.junit.Test;
import org.me.server.model.Exceptions.NotSignedInException;

import static org.junit.Assert.fail;

public class TestDatabaseUserDao_NotSignedIn {

    @Test(expected = NotSignedInException.class)
    public void deleteUser() throws Exception {
        fail("not implemented yet!");
    }

    @Test(expected = NotSignedInException.class)
    public void signOutUser_notSignedInThrowsException() throws Exception {
        fail("not implemented yet!");
    }

    @Test(expected = NotSignedInException.class)
    public void addFeed() throws Exception {
        fail("not implemented yet!");
    }

    @Test(expected = NotSignedInException.class)
    public void removeFeed() throws Exception {
        fail("not implemented yet!");
    }

    @Test(expected = NotSignedInException.class)
    public void likeFeed() throws Exception {
        fail("not implemented yet!");
    }

    @Test(expected = NotSignedInException.class)
    public void unlikeFeed() throws Exception {
        fail("not implemented yet!");
    }

    @Test(expected = NotSignedInException.class)
    public void followOtherUser() throws Exception {
        fail("not implemented yet!");
    }

    @Test(expected = NotSignedInException.class)
    public void unfollowOtherUser() throws Exception {
        fail("not implemented yet!");
    }

    @Test(expected = NotSignedInException.class)
    public void getUsersFollowedByMe() throws Exception {
        fail("not implemented yet!");
    }

}
