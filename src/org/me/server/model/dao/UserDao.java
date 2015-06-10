package org.me.server.model.dao;


import org.me.server.model.Exceptions.*;
import org.me.server.model.dto.Id;
import org.me.server.model.dto.Feed;
import org.me.server.model.dto.User;

import java.util.List;

public interface UserDao {

    void createUser(User newUser)
            throws UsernameAlreadyExistsException;
    void deleteUser(Id userId)
            throws UserDoesNotExistException;
    Id signInUser(User user)
            throws UserDoesNotExistException, IncorrectPasswordException;

    boolean existUser(String username);
    boolean existUser(User user);
    boolean existUser(Id userId);


//    void createUser(String username, String password);
//    Id signInUser(String username, String password);
//    void signOutUser(Id userId)
//        throws UserDoesNotExistException;

//    boolean isUserExist(Id userId);
//    Id getUserId(User user);
//    boolean isFeedExist(Id feedId);
//    Id getFeedId(Feed feed);

    // returns feed id
    Id addFeed(Id ownerId, Feed newFeed);
    void removeFeed(Id userId, Id feedId)
            throws FeedNotExistException, NoPrivilegeException;

    void likeFeed(Id userId, Id feedId)
            throws FeedNotExistException, AlreadyLikedException;
    void unlikeFeed(Id userId, Id feedId)
            throws FeedNotExistException, NotLikedBeforeException;

    void followOtherUser(Id userId, String otherUsername)
            throws UserDoesNotExistException, AlreadyFollowingException;
    void unfollowOtherUser(Id userId, String otherUsername)
            throws UserDoesNotExistException, NotFollowedBeforeException;

    List<String> getAllUsers();
    List<String> getUsersFollowedByMe(Id userId);

    Feed getFeed(Id feedId) throws FeedNotExistException;
    List<String> getFeedLikers(Id feedId) throws FeedNotExistException;
    List<Id> getFeedIdsFromUsersFollowedByMe(Id userId);

    List<Feed> getOtherUserPublicFeeds(String otherUsername) throws UserDoesNotExistException;

}
