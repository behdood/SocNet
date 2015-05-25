package org.me.client;


import java.util.Iterator;


public interface UserIX {

    ActionResult register(String username, String password);
    ActionResult login(String username, String password);
    ActionResult logout();

    Iterator<String> getAllUsers(String username);
    Iterator<String> getFollowedUsers(String username);
    Iterator<String> getFollowedFeeds(String username);
    Iterator<String> getPostLikers(String username, String post_id);
    Iterator<String> getPublicFeeds(String otherUsername);

    ActionResult sharePublic(String username, String message, String time_stamp, String post_id);
    ActionResult updateStatus(String username, String message, String time_stamp, String post_id);
    ActionResult deleteStatus(String username, String post_id);

    ActionResult follow(String username, String otherUsername);
    ActionResult unfollow(String username, String otherUsername);
    ActionResult like(String username, String post_id);
    ActionResult unlike(String username, String post_id);

    enum ActionResult {
        OK,
        USERNAME_EXIST_ERROR,
        USERNAME_NOT_EXIST_ERROR,
        INCORRECT_PASSWORD_ERROR,
        NOT_SIGNED_IN_ERROR,
        INVALID_POST_ID_ERROR,
        POST_DOES_NOT_EXIST_ERROR,
        ALREADY_FOLLOWING_ERROR,
        ALREADY_NOT_FOLLOWING_ERROR,
        ALREADY_LIKED_ERROR,
        ALREADY_NOT_LIKED_ERROR,
        DATABASE_CONNECTION_ERROR,
        UNKNOWN_ERROR
    }
}
