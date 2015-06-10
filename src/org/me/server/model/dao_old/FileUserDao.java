package org.me.server.model.dao_old;

import org.me.server.model.databases_old.Database;
import org.me.server.model.Exceptions.*;
import org.me.server.model.databases_old.FileDatabase;
import org.me.server.model.dto_old.*;

import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;


public class FileUserDao implements UserDao {

    private FileDatabase db;

    public FileUserDao(Database db) {
        this.db = (FileDatabase) db;
    }

    @Override
    public void signUp(String username, String password)
            throws UsernameAlreadyExistsException {
        if (db.allUsers.containsKey(username))
            throw new UsernameAlreadyExistsException();

        db.allUsers.put(username, new User(username, password));
        Vector<String> following = new Vector<>();
        following.add(username);
        db.followerMap.put(username, following);
    }

    @Override
    public void signIn(String username, String password)
            throws UserDoesNotExistException, IncorrectPasswordException {
        if (!db.allUsers.containsKey(username))
            throw new UserDoesNotExistException();

        User user = db.allUsers.get(username);
        if (!user.getPassword().equals(password))
            throw new IncorrectPasswordException();
    }

    @Override
    public void insertPost(String user, Feed p)
            throws NotSignedInException, InvalidPostIdException {
        if (!db.allUsers.containsKey(user))
            throw new NotSignedInException();

        if (db.allPosts.containsKey(p.getPost_id()))
            throw new InvalidPostIdException();

        db.allPosts.put(p.getPost_id(), p);
        db.postLikerMap.put(p.getPost_id(), new Vector<String>());
    }

    @Override
    public void deletePost(String user, String post_id)
            throws NotSignedInException, FeedNotExistException {
        if (!db.allUsers.containsKey(user))
            throw new NotSignedInException();

        if (!db.allPosts.containsKey(post_id))
            throw new FeedNotExistException();

        db.allPosts.remove(post_id);
    }

    @Override
    public void insertLike(String user, String post_id)
            throws NotSignedInException, FeedNotExistException, AlreadyLikedException {
        if (!db.allUsers.containsKey(user))
            throw new NotSignedInException();

        if (!db.allPosts.containsKey(post_id))
            throw new FeedNotExistException();

        Vector <String> currentLikers = db.postLikerMap.get(post_id);
        if (currentLikers.contains(user))
            throw new AlreadyLikedException(user);
        currentLikers.add(user);
        db.postLikerMap.put(post_id, currentLikers);
    }

    @Override
    public void deleteLike(String user, String post_id)
            throws NotSignedInException, FeedNotExistException, NotLikedBeforeException {
        if (!db.allUsers.containsKey(user))
            throw new NotSignedInException();

        if (!db.allPosts.containsKey(post_id))
            throw new FeedNotExistException();

        Vector <String> currentLikers = db.postLikerMap.get(post_id);
        if (!currentLikers.contains(user))
            throw new NotLikedBeforeException(user);

        currentLikers.remove(user);
        db.postLikerMap.put(post_id, currentLikers);
    }

    @Override
    public void insertFollow(String user, String other)
            throws NotSignedInException, AlreadyFollowingException, UserDoesNotExistException {
        if (!db.allUsers.containsKey(user))
            throw new NotSignedInException();
        if (!db.allUsers.containsKey(other))
            throw new UserDoesNotExistException(other);

        Vector<String> currentlyFollowing = db.followerMap.get(user);
        if (currentlyFollowing.contains(other))
            throw new AlreadyFollowingException(user, other);

        currentlyFollowing.add(other);
        db.followerMap.put(user, currentlyFollowing);
    }

    @Override
    public void deleteFollow(String user, String other)
            throws NotSignedInException, NotFollowedBeforeException, UserDoesNotExistException {
        if (!db.allUsers.containsKey(user))
            throw new NotSignedInException();
        if (!db.allUsers.containsKey(other))
            throw new UserDoesNotExistException(other);

        Vector<String> currentlyFollowing = db.followerMap.get(user);
        if (!currentlyFollowing.contains(other))
            throw new NotFollowedBeforeException(user, other);

        currentlyFollowing.remove(other);
        db.followerMap.put(user, currentlyFollowing);
    }

    @Override
    public Iterator<String> selectAllUsers(String user) throws NotSignedInException {
        if (!db.allUsers.containsKey(user))
            throw new NotSignedInException();

        return db.allUsers.keySet().iterator();
    }

    @Override
    public Iterator<String> selectFollowedUsers(String user)
            throws NotSignedInException {
        if (!db.allUsers.containsKey(user))
            throw new NotSignedInException();
        if (!db.followerMap.containsKey(user))
            throw new NotSignedInException();

        Vector<String> tmp = db.followerMap.get(user);
        Collections.sort(tmp);
        return tmp.iterator();
    }

    @Override
    public Iterator<String> selectFollowedPosts(String user) throws NotSignedInException {
        if (!db.allUsers.containsKey(user))
            throw new NotSignedInException();
        if (!db.followerMap.containsKey(user))
            throw new NotSignedInException();

        Vector<String> followed = db.followerMap.get(user);
        Vector<String> followedFeeds = new Vector<>();

        for (Feed feed : db.allPosts.values()) {
            if (followed.contains(feed.getOwner()))
                followedFeeds.add(feed.toString() + db.postLikerMap.get(feed.getPost_id()).size() + " likes");
        }
        Collections.reverse(followedFeeds);
        return followedFeeds.iterator();
    }

    @Override
    public Iterator<String> selectPostLikers(String user, String post_id) throws NotSignedInException, FeedNotExistException {
        if (!db.allUsers.containsKey(user))
            throw new NotSignedInException();
        if (!db.followerMap.containsKey(user))
            throw new NotSignedInException();
        if (!db.allPosts.containsKey(post_id))
            throw new FeedNotExistException();

//        Feed post = db.allPosts.get(post_id);
//        return db.postLikerMap.get(db.allPosts.get(post_id).getPost_id()).iterator();
        Vector<String> tmp = db.postLikerMap.get(post_id);
        Collections.sort(tmp);
        return tmp.iterator();
    }

    @Override
    public Iterator<String> selectPublicPosts(String other) throws UserDoesNotExistException {
        if (!db.allUsers.containsKey(other))
            throw new UserDoesNotExistException(other);

        Vector<String> followedFeeds = new Vector<>();
        for (Feed feed : db.allPosts.values()) {
            if (feed.getOwner().equals(other) && feed.getPrivacy().equals(Privacy.PUBLIC))
                followedFeeds.add(feed.toString() + db.postLikerMap.get(feed.getPost_id()).size() + " likes");
        }
        //TODO : return this in reverse order!
        return followedFeeds.iterator();
    }

}
