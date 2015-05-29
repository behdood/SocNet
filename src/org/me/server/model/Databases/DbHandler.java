package org.me.server.model.databases;


import org.me.server.model.dto.Feed;
import org.me.server.model.dto.User;

import java.util.Set;

public class DbHandler implements DbHandlerIX {

    BaseMapDB users;
    BaseMapDB posts;
    BaseMapOfSetsDB userOwnsPost;


    @Override
    public boolean createUser(User user) {
        return false;
    }

    @Override
    public boolean deleteUser(User user) {
        return false;
    }

    @Override
    public boolean createFeed(Feed feed) {
        return false;
    }

    @Override
    public boolean deleteFeed(Feed feed) {
        return false;
    }

    @Override
    public boolean followOtherUser(User user, User otherUser) {
        return false;
    }

    @Override
    public boolean unfollowOtherUser(User user, User otherUser) {
        return false;
    }

    @Override
    public boolean likesFeed(User user, Feed feed) {
        return false;
    }

    @Override
    public boolean unlikesFeed(User user, Feed feed) {
        return false;
    }

    @Override
    public Set<User> getListOfUsers(User user) {
        return null;
    }

    @Override
    public Set<User> getListOfUsersFollowedBy(User user) {
        return null;
    }

    @Override
    public Set<User> getListOfUsersFollowing(User user) {
        return null;
    }

    @Override
    public Set<User> getListOfFeedLikers(Feed feed) {
        return null;
    }

    @Override
    public Set<Feed> getOtherUserFeeds(User user, User otherUser) {
        return null;
    }
}
