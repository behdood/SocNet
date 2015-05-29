package org.me.server.model.bl;


import org.me.server.model.Id;
import org.me.server.model.dto.Feed;
import org.me.server.model.dto.User;

import java.util.List;

public class UserBL implements UserIX {
    @Override
    public UserActionResult register(String username, String password) {
        return null;
    }

    @Override
    public UserActionResult unregister() {
        return null;
    }

    @Override
    public UserActionResult login(String username, String password) {
        return null;
    }

    @Override
    public UserActionResult logout() {
        return null;
    }

    @Override
    public UserActionResult updateStatus(String statusText) {
        return null;
    }

    @Override
    public UserActionResult deleteStatus(Id statusId) {
        return null;
    }

    @Override
    public UserActionResult sharePublic(String statusText) {
        return null;
    }

    @Override
    public UserActionResult likeStatus(Id statusId) {
        return null;
    }

    @Override
    public UserActionResult unlikeStatus(Id statusId) {
        return null;
    }

    @Override
    public UserActionResult followOtherUser(String otherUsername) {
        return null;
    }

    @Override
    public UserActionResult unfollowOtherUser(String otherUsername) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public List<User> getUsersFollowedByMe() {
        return null;
    }

    @Override
    public List<User> getStatusLikers(Id statusId) {
        return null;
    }

    @Override
    public List<Feed> getAllFeeds() {
        return null;
    }

    @Override
    public List<Feed> getOtherUserPublicFeeds() {
        return null;
    }

}
