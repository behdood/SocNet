package org.me.server.model.dao;

import org.me.server.model.Exceptions.*;
import org.me.server.model.databases_new.LocalDatabaseHandler;
import org.me.server.model.dto.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class LocalStorageUserDao implements UserDao {

    private LocalDatabaseHandler dbHandler;

    public LocalStorageUserDao(LocalDatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    @Override
    public void createUser(User newUser) throws UsernameAlreadyExistsException {
        if (existUser(newUser))
            throw new UsernameAlreadyExistsException();

        dbHandler.insertIntoUsers(newUser);

        Id newUserId = dbHandler.getUserId(newUser);
        dbHandler.insertIntoFollows(newUserId, newUserId);
    }

    @Override
    public void deleteUser(Id userId) throws UserDoesNotExistException {
        if (!dbHandler.containsUser(userId))
            throw new UserDoesNotExistException();


        // remove user from anyone who follows it
        Set<User> allUsers = dbHandler.getAllUsers();
        for (User tmpUser : allUsers) {
            Id tmpId = dbHandler.getUserId(tmpUser);
            dbHandler.deleteFollowedFromFollows(tmpId, userId);
        }

        // remove user as a follower
        dbHandler.deleteFollowerFromFollows(userId);


        // remove user's feeds
        Set<Id> userFeedIds = dbHandler.getUserFeedsIds(userId);
        for (Id feedId : userFeedIds)
            try {
                removeFeed(userId, feedId);
            } catch (FeedNotExistException | NoPrivilegeException ignored) {
            }
        dbHandler.deleteUserFromUserOwnsFeeds(userId);

        // remove user from user table
        dbHandler.deleteFromUsers(userId);
    }

//    @Override
//    public Id signInUser(String username, String password) {}

    @Override
    public Id signInUser(User user) throws UserDoesNotExistException, IncorrectPasswordException {
        Id userId = dbHandler.getUserId(user);
        if (userId == null)
            throw new UserDoesNotExistException(user.getUsername());

        // check password
        User userInDb = dbHandler.getUser(userId);
        if (!user.getPassword().equals(userInDb.getPassword()))
            throw new IncorrectPasswordException();

        return userId;
    }

    @Override
    public boolean existUser(String username) {
        return existUser(makeTempUser(username));
    }

    @Override
    public boolean existUser(User user) {
        return dbHandler.containsUser(user);
    }

    @Override
    public boolean existUser(Id userId) {
        return dbHandler.containsUser(userId);
    }


//    private boolean isUserExist(Id userId) {
//        return false;
//    }
//
//    private Id getUserId(User user) {
//        return null;
//    }
//
//    private boolean isFeedExist(Id feedId) {
//        return false;
//    }
//
//    private Id getFeedId(Feed feed) {
//        return null;
//    }

    @Override
    public Id addFeed(Id ownerId, Feed newFeed) {
        Id feedId = dbHandler.insertIntoFeeds(newFeed);
        dbHandler.insertIntoUserOwnFeeds(ownerId, feedId);
        dbHandler.insertIntoLikes(feedId, null); // null means that only a new row is created for the feed
        return feedId;
    }

    @Override
    public void removeFeed(Id userId, Id feedId) throws FeedNotExistException, NoPrivilegeException {
        if (!dbHandler.containsFeed(feedId))
            throw new FeedNotExistException();

        if (!dbHandler.containsUserOwnsFeed(userId, feedId))
            throw new NoPrivilegeException();

        dbHandler.deleteFeedFromLikes(feedId);
        dbHandler.deleteFeedFromUserOwnsFeeds(userId, feedId);
        dbHandler.deleteFromFeeds(feedId);
    }

    @Override
    public void likeFeed(Id userId, Id feedId) throws FeedNotExistException, AlreadyLikedException {
        if (!dbHandler.containsFeed(feedId))
            throw new FeedNotExistException();
        if (dbHandler.containsLike(feedId, userId))
            throw new AlreadyLikedException();

        dbHandler.insertIntoLikes(feedId, userId);
    }

    @Override
    public void unlikeFeed(Id userId, Id feedId) throws FeedNotExistException, NotLikedBeforeException {
        if (!dbHandler.containsFeed(feedId))
            throw new FeedNotExistException();
        if (!dbHandler.containsLike(feedId, userId))
            throw new NotLikedBeforeException();

        dbHandler.deleteLikeFromLikes(feedId, userId);
    }

    @Override
    public void followOtherUser(Id userId, String otherUsername)
            throws UserDoesNotExistException, AlreadyFollowingException{
        User otherUser = makeTempUser(otherUsername);
        if (!existUser(otherUser))
            throw new UserDoesNotExistException(otherUsername);

        Id otherUserId = dbHandler.getUserId(otherUser);
        if (dbHandler.containsFollow(userId, otherUserId))
            throw new AlreadyFollowingException(dbHandler.getUser(userId).getUsername(), otherUsername);

        dbHandler.insertIntoFollows(userId, otherUserId);
    }

    @Override
    public void unfollowOtherUser(Id userId, String otherUsername)
            throws UserDoesNotExistException, NotFollowedBeforeException {
        User otherUser = makeTempUser(otherUsername);
        if (!existUser(otherUser))
            throw new UserDoesNotExistException(otherUsername);

        Id otherUserId = dbHandler.getUserId(otherUser);
        if (!dbHandler.containsFollow(userId, otherUserId))
            throw new NotFollowedBeforeException(dbHandler.getUser(userId).getUsername(), otherUsername);

        dbHandler.deleteFollowedFromFollows(userId, otherUserId);
    }

    @Override
    public List<String> getAllUsers() {
        List<String> user_names = new ArrayList<>();
        Set<User> userSet = dbHandler.getAllUsers();
        for (User user : userSet)
            user_names.add(user.getUsername());

        return user_names;
    }

    @Override
    public List<String> getUsersFollowedByMe(Id userId) {
        List<String> user_names = new ArrayList<>();
        Set<Id> followedUserIds = dbHandler.getFollowedUserIds(userId);

        for (Id followedId : followedUserIds)
            user_names.add(dbHandler.getUser(followedId).getUsername());

        return user_names;
    }

    @Override
    public Feed getFeed(Id feedId) throws FeedNotExistException {
        if (!dbHandler.containsFeed(feedId))
            throw new FeedNotExistException();

        return dbHandler.getFeed(feedId);
    }

    @Override
    public List<String> getFeedLikers(Id feedId) throws FeedNotExistException {
        if (!dbHandler.containsFeed(feedId))
            throw new FeedNotExistException();

        List<String> liker_names = new ArrayList<>();

        Set<Id> likerIdSet = dbHandler.getFeedLikersIds(feedId);

        for (Id likerId : likerIdSet) {
            liker_names.add(dbHandler.getUser(likerId).getUsername());
        }

        return liker_names;
    }

    @Override
    public List<Id> getFeedIdsFromUsersFollowedByMe(Id userId) {
        List<Id> feedIds = new ArrayList<>();

        Set<Id> followedUserIds = dbHandler.getFollowedUserIds(userId);
        for (Id followedId : followedUserIds) {
            Set<Id> followedUserFeeds = dbHandler.getUserFeedsIds(followedId);
            for (Id feedId : followedUserFeeds) {
                feedIds.add(feedId);
            }
        }
        return feedIds;
    }

    @Override
    public List<Feed> getOtherUserPublicFeeds(String otherUsername) throws UserDoesNotExistException {
        if (!dbHandler.containsUser(makeTempUser(otherUsername)))
            throw new UserDoesNotExistException(otherUsername);

        Id otherUserId = dbHandler.getUserId(makeTempUser(otherUsername));

        List<Feed> feeds = new ArrayList<>();
        Set<Id> otherUserFeeds = dbHandler.getUserFeedsIds(otherUserId);

        for (Id feedId : otherUserFeeds) {
            Feed feed = dbHandler.getFeed(feedId);
            if (feed.getPrivacy().equals(Privacy.PUBLIC))
                feeds.add(feed);
        }

        return feeds;
    }

    private boolean hasPrivilageOverFeed(Id userId, Id feedId){
        return dbHandler.isUserOwnFeed(userId, feedId);
    }

    private User makeTempUser(String username) {
        return new User(username, "");
    }
}
