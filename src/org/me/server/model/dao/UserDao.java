package org.me.server.model.dao;


import org.me.server.model.Id;
import org.me.server.model.bl.UserActionResult;
import org.me.server.model.dto.Feed;
import org.me.server.model.dto.Privacy;
import org.me.server.model.dto.User;

import java.util.List;

public interface UserDao {
    boolean createUser(String username, String password);
    boolean deleteUser(Id userId);
    Id signInUser(String username, String password);
    boolean signOutUser(Id userId);
    boolean isUserExist(Id userId);
    Id getUserId(User user);
    boolean isFeedExist(Id feedId);
    Id getFeedId(Feed feed);

    void addFeed(Id userId, Feed feed, Privacy privacy);
    void removeFeed(Id userId, Feed feed);

    void likeFeed(Id userId, Feed feed);
    void unlikeFeed(Id userId, Feed feed);

    void followOtherUser(Id userId, Id otherUserId);
    void unfollowOtherUser(Id userId, Id otherUserId);

    List<User> getAllUsers(Id userId);
    List<User> getUsersFollowedByMe(Id userId);
    List<User> getFeedLikers(Id userId, Id StatusId);

    List<Feed> getAllFeeds(Id userId);
    List<Feed> getOtherUserPublicFeeds(Id otherUserId);

}
