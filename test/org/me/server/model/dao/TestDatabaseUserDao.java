package org.me.server.model.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.me.server.model.Exceptions.UsernameAlreadyExistsException;
import org.me.server.model.Exceptions.UsernameDoesNotExistException;

import static org.junit.Assert.*;


public class TestDatabaseUserDao {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void createUser() throws Exception {
        fail("not implemented yet!");
    }
    @Test(expected = UsernameAlreadyExistsException.class)
    public void createUser_duplicateThrowsException() throws Exception {
        fail("not implemented yet!");
    }

    @Test
    public void deleteUser() throws Exception {
        fail("not implemented yet!");
    }
    @Test(expected = UsernameDoesNotExistException.class)
    public void deleteUser_notExistsThrowsException() throws Exception {
        fail("not implemented yet!");
    }

    @Test
    public void signInUser() throws Exception {
        fail("not implemented yet!");
    }
    @Test
    public void signInUser_userNotExistThrowsException() throws Exception {
        fail("not implemented yet!");
    }
    @Test
    public void signInUser_incorrectPasswordThrowsException() throws Exception {
        fail("not implemented yet!");
    }

    @Test
    public void signOutUser() throws Exception {
        fail("not implemented yet!");
    }

    @Test
    public void isUserExist() throws Exception {
        fail("not implemented yet!");
    }

    @Test
    public void getUserId() throws Exception {
        fail("not implemented yet!");
    }

    @Test
    public void isFeedExist() throws Exception {
        fail("not implemented yet!");
    }

    @Test
    public void getFeedId() throws Exception {
        fail("not implemented yet!");
    }

    @Test
    public void addFeed() throws Exception {
        fail("not implemented yet!");
    }

    @Test
    public void removeFeed() throws Exception {
        fail("not implemented yet!");
    }
    @Test
    public void removeFeed_feedNotExistThrowsException() throws Exception {
        fail("not implemented yet!");
    }
    @Test
    public void removeFeed_notOwnerThrowsException() throws Exception {
        fail("not implemented yet!");
    }

    @Test
    public void likeFeed() throws Exception {
        fail("not implemented yet!");
    }
    @Test
    public void likeFeed_likedBeforeThrowsException() throws Exception {
        fail("not implemented yet!");
    }

    @Test
    public void unlikeFeed() throws Exception {
        fail("not implemented yet!");
    }
    @Test
    public void unlikeFeed_notLikedBeforeThrowsException() throws Exception {
        fail("not implemented yet!");
    }

    @Test
    public void followOtherUser() throws Exception {
        fail("not implemented yet!");
    }
    @Test
    public void followOtherUser_otherUserNotExistThrowsException() throws Exception {
        fail("not implemented yet!");
    }
    @Test
    public void followOtherUser_alreadyFollowingThrowsException() throws Exception {
        fail("not implemented yet!");
    }

    @Test
    public void unfollowOtherUser() throws Exception {
        fail("not implemented yet!");
    }
    @Test
    public void unfollowOtherUser_otherUserNotExistThrowsException() throws Exception {
        fail("not implemented yet!");
    }
    @Test
    public void unfollowOtherUser_notFollowedBeforeThrowsException() throws Exception {
        fail("not implemented yet!");
    }

    @Test
    public void getAllUsers() throws Exception {
        fail("not implemented yet!");
    }

    @Test
    public void getUsersFollowedByMe() throws Exception {
        fail("not implemented yet!");
    }

    @Test
    public void getFeedLikers() throws Exception {
        fail("not implemented yet!");
    }
    @Test
    public void getFeedLikers_feedNotExistThrowsException() throws Exception {
        fail("not implemented yet!");
    }

    @Test
    public void getAllFeeds() throws Exception {
        fail("not implemented yet!");
    }

    @Test
    public void getOtherUserPublicFeeds() throws Exception {
        fail("not implemented yet!");
    }
    @Test(expected = UsernameDoesNotExistException.class)
    public void getOtherUserPublicFeeds_otherUserNotExistThrowsException() throws Exception {
        fail("not implemented yet!");
    }

}