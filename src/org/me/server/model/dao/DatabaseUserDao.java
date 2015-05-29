package org.me.server.model.dao;


import org.me.server.model.Id;
import org.me.server.model.dto.Feed;
import org.me.server.model.dto.Privacy;
import org.me.server.model.dto.User;

import java.util.List;

public class DatabaseUserDao implements UserDao {
    @Override
    public boolean createUser(String username, String password) {

    }

    @Override
    public boolean deleteUser(Id userId) {

    }

    @Override
    public Id signInUser(String username, String password) {

    }

    @Override
    public boolean signOutUser(Id userId) {

    }

    @Override
    public boolean isUserExist(Id userId) {
        return false;
    }

    @Override
    public Id getUserId(User user) {
        return null;
    }

    @Override
    public boolean isFeedExist(Id feedId) {
        return false;
    }

    @Override
    public Id getFeedId(Feed feed) {
        return null;
    }

    @Override
    public void addFeed(Id userId, Feed feed, Privacy privacy) {

    }

    @Override
    public void removeFeed(Id userId, Feed feed) {

    }

    @Override
    public void likeFeed(Id userId, Feed feed) {

    }

    @Override
    public void unlikeFeed(Id userId, Feed feed) {

    }

    @Override
    public void followOtherUser(Id userId, Id otherUserId) {

    }

    @Override
    public void unfollowOtherUser(Id userId, Id otherUserId) {

    }

    @Override
    public List<User> getAllUsers(Id userId) {
        return null;
    }

    @Override
    public List<User> getUsersFollowedByMe(Id userId) {
        return null;
    }

    @Override
    public List<User> getFeedLikers(Id userId, Id StatusId) {
        return null;
    }

    @Override
    public List<Feed> getAllFeeds(Id userId) {
        return null;
    }

    @Override
    public List<Feed> getOtherUserPublicFeeds(Id otherUserId) {
        return null;
    }
}
