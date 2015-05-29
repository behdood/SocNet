package org.me.server.model.bl;


import org.me.server.model.Id;
import org.me.server.model.dto.Feed;
import org.me.server.model.dto.User;

import java.util.List;

// implementations of this interface should store the current user
public interface UserIX {

    UserActionResult register(String username, String password);
    UserActionResult unregister();
    UserActionResult login(String username, String password);
    UserActionResult logout();

    UserActionResult updateStatus(String statusText);
    UserActionResult deleteStatus(Id statusId);
    UserActionResult sharePublic(String statusText);

    UserActionResult likeStatus(Id statusId);
    UserActionResult unlikeStatus(Id statusId);

    UserActionResult followOtherUser(String otherUsername);
    UserActionResult unfollowOtherUser(String otherUsername);

    List<User> getAllUsers();
    List<User> getUsersFollowedByMe();
    List<User> getStatusLikers(Id statusId);

    List<Feed> getAllFeeds();
    List<Feed> getOtherUserPublicFeeds();



}
