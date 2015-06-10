package org.me.server.model.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.me.server.model.Exceptions.*;
import org.me.server.model.databases.local.LocalDatabaseHandler;
import org.me.server.model.dto.Feed;
import org.me.server.model.dto.Privacy;
import org.me.server.model.dto.User;
import org.me.server.model.dto.Id;

import java.util.List;

import static org.junit.Assert.*;


public class TestLocalStorageUserDao_SignedInUserOperations {

    UserDao userDao;
//    User temp_user1, temp_user2;
    Id temp_user_id1, temp_user_id2;

    @Before
    public void setUp() throws Exception {
        LocalDatabaseHandler dbHandler = new LocalDatabaseHandler(true);
        userDao = new LocalStorageUserDao(dbHandler);

        User temp_user1 = getFakeUser(1);
        User temp_user2 = getFakeUser(2);

        userDao.createUser(temp_user1);
        userDao.createUser(temp_user2);
        temp_user_id1 = userDao.signInUser(temp_user1);
        temp_user_id2 = userDao.signInUser(temp_user2);
    }

    @After
    public void tearDown() throws Exception {
//        userDao.deleteUser(temp_user1);
    }


//    @Test
//    public void isUserExist() throws Exception {
//        fail("not implemented yet!");
//    }
//
//    @Test
//    public void getUserId() throws Exception {
//        fail("not implemented yet!");
//    }
//
//    @Test
//    public void isFeedExist() throws Exception {
//        fail("not implemented yet!");
//    }
//
//    @Test
//    public void getFeedId() throws Exception {
//        fail("not implemented yet!");
//    }

    @Test
    public void addFeed() throws Exception {
        Feed feed = getFakePrivateFeed();

        Id feed_id = userDao.addFeed(temp_user_id1, feed);

        assertTrue(getTempDbHandler().containsFeed(feed_id));
        assertEquals(getTempDbHandler().getFeed(feed_id), feed);
    }

    @Test
    public void removeFeed() throws Exception {
        Id feed_id = userDao.addFeed(temp_user_id1, getFakePrivateFeed());

        userDao.removeFeed(temp_user_id1, feed_id);

        assertFalse(getTempDbHandler().containsFeed(feed_id));
    }
    @Test(expected = FeedNotExistException.class)
    public void removeFeed_feedNotExistThrowsException() throws Exception {
        Id non_existent_id = new Id("-1");

        userDao.removeFeed(temp_user_id1, non_existent_id);
    }
    @Test(expected = NoPrivilegeException.class)
    public void removeFeed_noPrivilegeThrowsException() throws Exception {
        Id feed_id = userDao.addFeed(temp_user_id1, getFakePrivateFeed());

        userDao.removeFeed(temp_user_id2, feed_id);
    }

    @Test
    public void likeFeed() throws Exception {
        Feed feed = getFakePrivateFeed();
        Id feed_id = userDao.addFeed(temp_user_id1, feed);

        userDao.likeFeed(temp_user_id2, feed_id);

        assertTrue(getTempDbHandler().containsLike(feed_id, temp_user_id2));
        assertFalse(getTempDbHandler().containsLike(feed_id, temp_user_id1));
    }
    @Test(expected = FeedNotExistException.class)
    public void likeFeed_feedNotExistThrowsException() throws Exception {
        Id non_existent_feed_id = new Id("-1");

        userDao.likeFeed(temp_user_id1, non_existent_feed_id);
    }
    @Test(expected = AlreadyLikedException.class)
    public void likeFeed_likedBeforeThrowsException() throws Exception {
        Feed feed = getFakePrivateFeed();
        Id feed_id = userDao.addFeed(temp_user_id1, feed);

        userDao.likeFeed(temp_user_id2, feed_id);
        userDao.likeFeed(temp_user_id2, feed_id);
    }

    @Test
    public void unlikeFeed() throws Exception {
        Feed feed = getFakePrivateFeed();
        Id feed_id = userDao.addFeed(temp_user_id1, feed);
        userDao.likeFeed(temp_user_id2, feed_id);

        userDao.unlikeFeed(temp_user_id2, feed_id);

        assertFalse(getTempDbHandler().containsLike(feed_id, temp_user_id2));
    }
    @Test(expected = FeedNotExistException.class)
    public void unlikeFeed_feedNotExistThrowsException() throws Exception {
        Id non_existent_feed_id = new Id("-1");

        userDao.likeFeed(temp_user_id1, non_existent_feed_id);
    }
    @Test(expected = NotLikedBeforeException.class)
    public void unlikeFeed_notLikedBeforeThrowsException() throws Exception {
        Feed feed = getFakePrivateFeed();
        Id feed_id = userDao.addFeed(temp_user_id1, feed);

        userDao.unlikeFeed(temp_user_id2, feed_id);
    }

    @Test
    public void followOtherUser() throws Exception {
        userDao.followOtherUser(temp_user_id1, "temp_user2");

        assertTrue(getTempDbHandler().containsFollow(temp_user_id1, temp_user_id2));
        // each user should also follow him/her self
        assertTrue(getTempDbHandler().containsFollow(temp_user_id1, temp_user_id1));
        assertTrue(getTempDbHandler().containsFollow(temp_user_id2, temp_user_id2));
    }
    @Test(expected = UserDoesNotExistException.class)
    public void followOtherUser_otherUserNotExistThrowsException() throws Exception {
        String non_existent_username = "non_exist";
        userDao.followOtherUser(temp_user_id1, non_existent_username);
    }
    @Test(expected = AlreadyFollowingException.class)
    public void followOtherUser_alreadyFollowingThrowsException() throws Exception {
        userDao.followOtherUser(temp_user_id1, "temp_user2");
        userDao.followOtherUser(temp_user_id1, "temp_user2");
    }

    @Test
    public void unfollowOtherUser() throws Exception {
        userDao.followOtherUser(temp_user_id1, "temp_user2");

        userDao.unfollowOtherUser(temp_user_id1, "temp_user2");

        assertFalse(getTempDbHandler().containsFollow(temp_user_id1, temp_user_id2));
    }
    @Test(expected = UserDoesNotExistException.class)
    public void unfollowOtherUser_otherUserNotExistThrowsException() throws Exception {
        String non_existent_username = "non_exist";

        userDao.unfollowOtherUser(temp_user_id1, non_existent_username);
    }
    @Test(expected = NotFollowedBeforeException.class)
    public void unfollowOtherUser_notFollowedBeforeThrowsException() throws Exception {
        userDao.unfollowOtherUser(temp_user_id1, "temp_user2");
    }

    @Test
    public void getAllUsers() throws Exception {
        User newUser = getFakeUser("newUser", "newPass");
        userDao.createUser(newUser);


        List<String> userList = userDao.getAllUsers();

        // at the beginning, there should be two users in the table
        assertEquals(userList.size(), 3);
        assertTrue(userList.contains(newUser.getUsername()));

        assertTrue(userList.contains("temp_user1"));
        assertTrue(userList.contains("temp_user2"));
    }

    @Test
    public void getUsersFollowedByMe() throws Exception {
        // each user follows him/herself, make user1 follow user2
        userDao.followOtherUser(temp_user_id1, "temp_user2");

        List<String> userListFollowedBy1 = userDao.getUsersFollowedByMe(temp_user_id1);
        List<String> userListFollowedBy2 = userDao.getUsersFollowedByMe(temp_user_id2);

        // for user 1
        assertEquals(userListFollowedBy1.size(), 2);
        assertTrue(userListFollowedBy1.contains("temp_user1"));
        assertTrue(userListFollowedBy1.contains("temp_user2"));

        // for user 2
        assertEquals(userListFollowedBy2.size(), 1);
        assertTrue(userListFollowedBy2.contains("temp_user2"));
    }

    @Test
    public void getFeed() throws Exception {
        Feed feed1 = getFakePrivateFeed();
        Feed feed2 = getFakePublicFeed();
        Id feed_id1 = userDao.addFeed(temp_user_id1, feed1);
        Id feed_id2 = userDao.addFeed(temp_user_id2, feed2);

        Feed feed1_returned = userDao.getFeed(feed_id1);
        Feed feed2_returned = userDao.getFeed(feed_id2);

        assertEquals(feed1_returned, feed1);
        assertEquals(feed2_returned, feed2);
    }
    @Test(expected = FeedNotExistException.class)
    public void getFeed_feedNotExistThrowsException() throws Exception {
        Id non_existent_feed_id = new Id("-1");

        userDao.getFeed(non_existent_feed_id);
    }

    @Test
    public void getFeedLikers() throws Exception {
        Id feed_id = userDao.addFeed(temp_user_id1, getFakePrivateFeed());
        userDao.likeFeed(temp_user_id1, feed_id);
        userDao.likeFeed(temp_user_id2, feed_id);

        List<String> feedLikers = userDao.getFeedLikers(feed_id);

        assertEquals(feedLikers.size(), 2);
        assertTrue(feedLikers.contains("temp_user1"));
        assertTrue(feedLikers.contains("temp_user2"));
    }
    @Test(expected = FeedNotExistException.class)
    public void getFeedLikers_feedNotExistThrowsException() throws Exception {
        Id non_existent_feed_id = new Id("-1");

        userDao.getFeedLikers(non_existent_feed_id);
    }

    @Test
    public void getAllFeedsFromFollowed() throws Exception {
        Feed public_feed1 = getFakePublicFeed();
        Feed private_feed1 = getFakePrivateFeed();
        Feed private_feed2 = getFakePrivateFeed();
        Id feed_id11pu = userDao.addFeed(temp_user_id1, public_feed1);
        Id feed_id11pr = userDao.addFeed(temp_user_id1, private_feed1);
        Id feed_id22pr = userDao.addFeed(temp_user_id2, private_feed2);

        List<Id> feeds_before_following = userDao.getFeedIdsFromUsersFollowedByMe(temp_user_id2);
        userDao.followOtherUser(temp_user_id2, "temp_user1");
        List<Id> feeds_after_following = userDao.getFeedIdsFromUsersFollowedByMe(temp_user_id2);

        // before following
        assertEquals(feeds_before_following.size(), 1);
        assertTrue(feeds_after_following.contains(feed_id22pr));

        // after following
        assertEquals(feeds_after_following.size(), 3);
        assertTrue(feeds_after_following.contains(feed_id11pu));
        assertTrue(feeds_after_following.contains(feed_id11pr));
        assertTrue(feeds_after_following.contains(feed_id22pr));
    }

    @Test
    public void getOtherUserPublicFeeds() throws Exception {
        Feed public_feed1 = getFakePublicFeed();
        Feed public_feed2 = getFakePublicFeed();
        Feed private_feed = getFakePrivateFeed();
        userDao.addFeed(temp_user_id1, public_feed1);
        userDao.addFeed(temp_user_id1, public_feed2);
        userDao.addFeed(temp_user_id1, private_feed);

        List<Feed> feeds = userDao.getOtherUserPublicFeeds("temp_user1");


        assertTrue(feeds.contains(public_feed1));
        assertTrue(feeds.contains(public_feed2));
        assertFalse(feeds.contains(private_feed));
    }
    @Test(expected = UserDoesNotExistException.class)
    public void getOtherUserPublicFeeds_otherUserNotExistThrowsException() throws Exception {
        String non_existent_username = "non_existent";
        userDao.getOtherUserPublicFeeds(non_existent_username);
    }



    private User getFakeUser(int index) {
        return getFakeUser("temp_user" + index, "temp_pass" + index);
    }
    private User getFakeUser(String username, String password) {
        return new User(username, password);
    }

    private Feed getFakePrivateFeed() {
        return new Feed("this is a private feed.", Privacy.PRIVATE);
    }
    private Feed getFakePublicFeed() {
        return new Feed("this is a public feed.", Privacy.PUBLIC);
    }

    private LocalDatabaseHandler getTempDbHandler() {
        return new LocalDatabaseHandler();
    }

}