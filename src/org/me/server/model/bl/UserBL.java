package org.me.server.model.bl;


import org.me.server.model.dto.Id;
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

    public String getCommandsList(boolean isLoggedIn) {
        String s = "";

        String LINE_SEPARATOR = "\n";
        String INDENT = "    ";

        if (isLoggedIn) {
            s += INDENT + "logout" + LINE_SEPARATOR;
            s += INDENT + "delete_account" + LINE_SEPARATOR;
            s += INDENT + "create_status <text>" + LINE_SEPARATOR;
            s += INDENT + "create_public_status <text>" + LINE_SEPARATOR;
            s += INDENT + "modify_status <status_id>" + LINE_SEPARATOR;
            s += INDENT + "delete_status <status_id>" + LINE_SEPARATOR;
            s += INDENT + "like_status <status_id>" + LINE_SEPARATOR;
            s += INDENT + "unlike_status <status_id>" + LINE_SEPARATOR;
            s += INDENT + "follow_user <username>" + LINE_SEPARATOR;
            s += INDENT + "unfollow_user <username>" + LINE_SEPARATOR;
            s += INDENT + "get_all_users" + LINE_SEPARATOR;
            s += INDENT + "get_all_feeds" + LINE_SEPARATOR;
            s += INDENT + "i_follow" + LINE_SEPARATOR;
            s += INDENT + "follows_me" + LINE_SEPARATOR;
            s += INDENT + "get_public_feeds <username>" + LINE_SEPARATOR;
        } else {
            s += INDENT + "create_account <username> <password>" + LINE_SEPARATOR;
            s += INDENT + "login <username> <password>" + LINE_SEPARATOR;
        }
        return s;
    }

}
