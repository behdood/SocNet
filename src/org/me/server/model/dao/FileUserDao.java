package org.me.server.model.dao;

import org.me.server.model.Databases.Database;
import org.me.server.model.Exceptions.*;
import org.me.server.model.dto.*;

import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;


public class FileUserDao implements UserDao {

    private org.me.server.model.Databases.FileDatabase db;

    public FileUserDao(Database db) {
        this.db = (org.me.server.model.Databases.FileDatabase) db;
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
            throws UsernameDoesNotExistException, IncorrectPasswordException {
        if (!db.allUsers.containsKey(username))
            throw new UsernameDoesNotExistException();

        User user = db.allUsers.get(username);
        if (!user.getPassword().equals(password))
            throw new IncorrectPasswordException();
    }

    @Override
    public void insertPost(String user, Post p)
            throws NotSignedInException, InvalidPostIdException {
        if (!db.allUsers.containsKey(user))
            throw new NotSignedInException();

        if (db.allPosts.containsKey(p.getId()))
            throw new InvalidPostIdException();

        db.allPosts.put(p.getId(), p);
        db.postLikerMap.put(p.getId(), new Vector<String>());
    }

    @Override
    public void deletePost(String user, String post_id)
            throws NotSignedInException, PostDoesNotExistException {
        if (!db.allUsers.containsKey(user))
            throw new NotSignedInException();

        if (!db.allPosts.containsKey(post_id))
            throw new PostDoesNotExistException();

        db.allPosts.remove(post_id);
    }

    @Override
    public void insertLike(String user, String post_id)
            throws NotSignedInException, PostDoesNotExistException, AlreadyLikedException {
        if (!db.allUsers.containsKey(user))
            throw new NotSignedInException();

        if (!db.allPosts.containsKey(post_id))
            throw new PostDoesNotExistException();

        Vector <String> currentLikers = db.postLikerMap.get(post_id);
        if (currentLikers.contains(user))
            throw new AlreadyLikedException(user);
        currentLikers.add(user);
        db.postLikerMap.put(post_id, currentLikers);
    }

    @Override
    public void deleteLike(String user, String post_id)
            throws NotSignedInException, PostDoesNotExistException, NotLikedBeforeException {
        if (!db.allUsers.containsKey(user))
            throw new NotSignedInException();

        if (!db.allPosts.containsKey(post_id))
            throw new PostDoesNotExistException();

        Vector <String> currentLikers = db.postLikerMap.get(post_id);
        if (!currentLikers.contains(user))
            throw new NotLikedBeforeException(user);

        currentLikers.remove(user);
        db.postLikerMap.put(post_id, currentLikers);
    }

    @Override
    public void insertFollow(String user, String other)
            throws NotSignedInException, AlreadyFollowingException, UsernameDoesNotExistException {
        if (!db.allUsers.containsKey(user))
            throw new NotSignedInException();
        if (!db.allUsers.containsKey(other))
            throw new UsernameDoesNotExistException(other);

        Vector<String> currentlyFollowing = db.followerMap.get(user);
        if (currentlyFollowing.contains(other))
            throw new AlreadyFollowingException(user, other);

        currentlyFollowing.add(other);
        db.followerMap.put(user, currentlyFollowing);
    }

    @Override
    public void deleteFollow(String user, String other)
            throws NotSignedInException, NotFollowingException, UsernameDoesNotExistException {
        if (!db.allUsers.containsKey(user))
            throw new NotSignedInException();
        if (!db.allUsers.containsKey(other))
            throw new UsernameDoesNotExistException(other);

        Vector<String> currentlyFollowing = db.followerMap.get(user);
        if (!currentlyFollowing.contains(other))
            throw new NotFollowingException(user, other);

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

        for (Post post : db.allPosts.values()) {
            if (followed.contains(post.getOwner()))
                followedFeeds.add(post.toString() + db.postLikerMap.get(post.getId()).size() + " likes");
        }
        Collections.reverse(followedFeeds);
        return followedFeeds.iterator();
    }

    @Override
    public Iterator<String> selectPostLikers(String user, String post_id) throws NotSignedInException, PostDoesNotExistException {
        if (!db.allUsers.containsKey(user))
            throw new NotSignedInException();
        if (!db.followerMap.containsKey(user))
            throw new NotSignedInException();
        if (!db.allPosts.containsKey(post_id))
            throw new PostDoesNotExistException();

//        Post post = db.allPosts.get(post_id);
//        return db.postLikerMap.get(db.allPosts.get(post_id).getId()).iterator();
        Vector<String> tmp = db.postLikerMap.get(post_id);
        Collections.sort(tmp);
        return tmp.iterator();
    }

    @Override
    public Iterator<String> selectPublicPosts(String other) throws UsernameDoesNotExistException {
        if (!db.allUsers.containsKey(other))
            throw new UsernameDoesNotExistException(other);

        Vector<String> followedFeeds = new Vector<>();
        for (Post post : db.allPosts.values()) {
            if (post.getOwner().equals(other) && post.getPrivacy().equals(Privacy.PUBLIC))
                followedFeeds.add(post.toString() + db.postLikerMap.get(post.getId()).size() + " likes");
        }
        //TODO : return this in reverse order!
        return followedFeeds.iterator();
    }

}
