package org.me.server.model.dao;


import org.me.server.model.Exceptions.FeedNotExistException;
import org.me.server.model.Exceptions.NoPrivilegeException;
import org.me.server.model.dto.Id;
import org.me.server.model.dto.Feed;
import org.me.server.model.dto.User;

import java.util.List;

public class DatabaseUserDao implements UserDao {
    @Override
    public void createUser(User newUser) {

    }

    @Override
    public void deleteUser(Id userId) {

    }

    @Override
    public Id signInUser(User user) {
        return null;
    }

    @Override
    public boolean existUser(String username) {
        return false;
    }

    @Override
    public boolean existUser(User user) {
        return false;
    }

    @Override
    public boolean existUser(Id userId) {
        return false;
    }

//    @Override
//    public void signOutUser(Id userId) {}

    @Override
    public Id addFeed(Id ownerId, Feed newFeed) {
        return null;
    }

    @Override
    public void removeFeed(Id userId, Id feedId) throws FeedNotExistException, NoPrivilegeException {

    }

    @Override
    public void likeFeed(Id userId, Id feedId) {

    }

    @Override
    public void unlikeFeed(Id userId, Id feedId) {

    }

    @Override
    public void followOtherUser(Id userId, String otherUsername) {

    }

    @Override
    public void unfollowOtherUser(Id userId, String otherUsername) {

    }

    @Override
    public List<String> getAllUsers() {
        return null;
    }

    @Override
    public List<String> getUsersFollowedByMe(Id userId) {
        return null;
    }

    @Override
    public Feed getFeed(Id feedId) throws FeedNotExistException {
        return null;
    }

    @Override
    public List<String> getFeedLikers(Id feedId) {
        return null;
    }

    @Override
    public List<Id> getFeedIdsFromUsersFollowedByMe(Id userId) {
        return null;
    }

    @Override
    public List<Feed> getOtherUserPublicFeeds(String otherUsername) {
        return null;
    }
}
