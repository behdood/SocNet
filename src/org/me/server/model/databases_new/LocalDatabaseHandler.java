package org.me.server.model.databases_new;


import org.me.server.model.dto.Feed;
import org.me.server.model.dto.Id;
import org.me.server.model.dto.IdManager;
import org.me.server.model.dto.User;

import java.util.Set;

public class LocalDatabaseHandler {
//    public enum TABLE_TYPE {USER_TABLE, FEED_TABLE, LIKE_TABLE, FOLLOW_TABLE, FEED_OWNER_TABLE};
//    public static final int USER_TABLE = 1;
//    public static final int FEED_TABLE = 2;
//    public static final int LIKE_TABLE = 3;
//    public static final int FOLLOW_TABLE = 4;
//    public static final int FEED_OWNER_TABLE = 5;

    private LocalDatabase db;
    private IdManager idManager;

    public LocalDatabaseHandler() {
        this(false);
    }

    public LocalDatabaseHandler(boolean resetDatabase) {
        this.idManager = IdManager.getInstance();
        this.db = LocalDatabase.getInstance(resetDatabase);
    }

    //    public void insertInto(TABLE_TYPE tableType, Object obj ) {
//
//    }
//
//    public boolean tableContainPair(TABLE_TYPE tableType, Id key , Object value) {
//        return false;
//    }

    public void insertIntoUsers(User user) {
        User user_copy = new User(user.getUsername(), user.getPassword());
        User user_without_password = new User(user.getUsername(), "");

        Id userId = idManager.getNextUserId();

        db.USERS_TABLE.put(userId, user_copy);
        db.USERS_TABLE_REVERSED.put(user_without_password, userId);
    }

    public Id insertIntoFeeds(Feed feed) {
        Id feedId = idManager.getNextFeedId();

        db.FEEDS_TABLE.put(feedId, feed);
        return feedId;
    }

    public void insertIntoUserOwnFeeds(Id userId, Id feedId) {
        Set<Id> feedIdSet;
        feedIdSet = getIdSet("FEED_OWNERS_TABLE", userId);
//        if (db.USER_OWN_FEEDS_TABLE.containsKey(userId))
//            feedIdSet = db.USER_OWN_FEEDS_TABLE.get(userId);
//        else
//            feedIdSet = db.getNewIdSet();

        feedIdSet.add(feedId);
        db.USER_OWN_FEEDS_TABLE.put(userId, feedIdSet);
    }

    public void insertIntoLikes(Id feedId, Id likerId) {
        Set<Id> likerIdSet;
        likerIdSet = getIdSet("LIKES_TABLE", feedId);
//        if (db.FEED_LIKED_BY_USER_TABLE.containsKey(feedId))
//            likerIdSet = db.FEED_LIKED_BY_USER_TABLE.get(feedId);
//        else
//            likerIdSet = db.getNewIdSet();

        if (likerId != null)
            likerIdSet.add(likerId);
        db.FEED_LIKED_BY_USER_TABLE.put(feedId, likerIdSet);
    }

    public void insertIntoFollows(Id followerUserId, Id followedUserId) {
        Set<Id> followedIdSet;
        followedIdSet = getIdSet("FOLLOWS_TABLE", followerUserId);
//        if (db.USER_FOLLOWS_USERS_TABLE.containsKey(followerUserId))
//            followedIdSet = db.USER_FOLLOWS_USERS_TABLE.get(followerUserId);
//        else
//            followedIdSet = new HashSet<>();


        followedIdSet.add(followedUserId);
        db.USER_FOLLOWS_USERS_TABLE.put(followerUserId, followedIdSet);
    }


    public void deleteFromUsers(Id userId) {
        User user = db.USERS_TABLE.get(userId);
        db.USERS_TABLE.remove(userId);
        db.USERS_TABLE_REVERSED.remove(user);
    }

    public void deleteFromFeeds(Id feedId) {
        db.FEEDS_TABLE.remove(feedId);
    }

    public void deleteFeedFromUserOwnsFeeds(Id userId, Id feedId) {
        Set<Id> feedIdSet = db.USER_OWN_FEEDS_TABLE.get(userId);
        feedIdSet.remove(feedId);
        db.USER_OWN_FEEDS_TABLE.put(userId, feedIdSet);
    }
    public void deleteUserFromUserOwnsFeeds(Id userId) {
        db.USER_OWN_FEEDS_TABLE.remove(userId);
    }

    public void deleteLikeFromLikes(Id feedId, Id likerId) {
        Set<Id> userIdSet = db.FEED_LIKED_BY_USER_TABLE.get(feedId);
        userIdSet.remove(likerId);
        db.USER_OWN_FEEDS_TABLE.put(feedId, userIdSet);
    }
    public void deleteFeedFromLikes(Id feedId) {
        db.FEED_LIKED_BY_USER_TABLE.remove(feedId);
    }

    public void deleteFollowedFromFollows(Id followerId, Id followedId) {
        Set<Id> followedIdSet = db.USER_FOLLOWS_USERS_TABLE.get(followerId);
        followedIdSet.remove(followedId);
        db.USER_OWN_FEEDS_TABLE.put(followerId, followedIdSet);
    }
    public void deleteFollowerFromFollows(Id followerId) {
        db.USER_FOLLOWS_USERS_TABLE.remove(followerId);
    }


    public boolean containsUser(Id userId) {
        return db.USERS_TABLE.containsKey(userId);
    }
    public boolean containsUser(User user) {
        return db.USERS_TABLE_REVERSED.containsKey(user);
    }

    public boolean containsFeed(Id feedId) {
        return db.FEEDS_TABLE.containsKey(feedId);
    }
    public boolean containsFeed(Feed feed) {
        // todo : check this! is this method necessary?!
        return db.FEEDS_TABLE.containsValue(feed);
    }

    public boolean containsUserOwnsFeed(Id userId, Id feedId) {
        return db.USER_OWN_FEEDS_TABLE.containsKey(userId) && db.USER_OWN_FEEDS_TABLE.get(userId).contains(feedId);
    }

    public boolean containsLike(Id feedId, Id userId) {
        return db.FEED_LIKED_BY_USER_TABLE.containsKey(feedId)
                && db.FEED_LIKED_BY_USER_TABLE.get(feedId).contains(userId);
    }

    public boolean containsFollow(Id userId, Id followedId) {
        return db.USER_FOLLOWS_USERS_TABLE.containsKey(userId)
                && db.USER_FOLLOWS_USERS_TABLE.get(userId).contains(followedId);
    }

    public User getUser(Id userId) {
        return db.USERS_TABLE.get(userId);
    }

    public Id getUserId(User user) {
        return db.USERS_TABLE_REVERSED.get(user);
    }

    public Feed getFeed(Id feedId) {
        return db.FEEDS_TABLE.get(feedId);
    }

    public Set<Id> getUserFeedsIds(Id userId) {
        return db.USER_OWN_FEEDS_TABLE.get(userId);
    }

    public Set<Id> getFeedLikersIds(Id feedId) {
        return db.FEED_LIKED_BY_USER_TABLE.get(feedId);
    }

    public Set<Id> getFollowedUserIds(Id followerId) {
        return db.USER_FOLLOWS_USERS_TABLE.get(followerId);
    }

    public Set<User> getAllUsers() {
        return db.USERS_TABLE_REVERSED.keySet();
    }

    public boolean isUserOwnFeed(Id userId, Id feedId) {
        return db.USER_OWN_FEEDS_TABLE.get(userId).contains(feedId);
    }

    private Set<Id> getIdSet(String tableName, Id id) {
        if (tableName.equalsIgnoreCase("FEED_OWNERS_TABLE") && db.USER_OWN_FEEDS_TABLE.containsKey(id))
            return db.USER_OWN_FEEDS_TABLE.get(id);
        else if (tableName.equalsIgnoreCase("LIKES_TABLE") && db.FEED_LIKED_BY_USER_TABLE.containsKey(id))
            return db.FEED_LIKED_BY_USER_TABLE.get(id);
        else if (tableName.equalsIgnoreCase("FOLLOWS_TABLE") && db.USER_FOLLOWS_USERS_TABLE.containsKey(id))
            return db.USER_FOLLOWS_USERS_TABLE.get(id);
        else
            return db.getNewIdSet();
    }


}
