package org.me.server.model.databases;


import org.me.server.model.dto.Feed;
import org.me.server.model.dto.Privacy;
import org.me.server.model.dto.User;

import java.util.Set;

public interface DbHandlerIX {
    boolean createUser(User user);
    boolean deleteUser(User user);

    boolean createFeed(Feed feed);
    boolean deleteFeed(Feed feed);

    boolean followOtherUser(User user, User otherUser);
    boolean unfollowOtherUser(User user, User otherUser);

    boolean likesFeed(User user, Feed feed);
    boolean unlikesFeed(User user, Feed feed);

    Set<User> getListOfUsers(User user);
    Set<User> getListOfUsersFollowedBy(User user);
    Set<User> getListOfUsersFollowing(User user);

    Set<User> getListOfFeedLikers(Feed feed);

    Set<Feed> getOtherUserFeeds(User user, User otherUser);
}
