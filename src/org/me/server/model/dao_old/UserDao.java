package org.me.server.model.dao_old;

import org.me.server.model.dto_old.Feed;
import org.me.server.model.Exceptions.*;

import java.sql.SQLException;
import java.util.Iterator;


public interface UserDao {
    void signUp(String username, String password)
            throws UsernameAlreadyExistsException, SQLException;
    void signIn(String username, String password)
            throws UserDoesNotExistException, IncorrectPasswordException, SQLException;

    void insertPost(String user, Feed p)
            throws NotSignedInException, InvalidPostIdException, SQLException;
    void deletePost(String user, String post_id)
            throws NotSignedInException, FeedNotExistException, SQLException;

    void insertLike(String user, String post_id)
            throws NotSignedInException, FeedNotExistException, AlreadyLikedException, SQLException;
    void deleteLike(String user, String post_id)
            throws NotSignedInException, FeedNotExistException, NotLikedBeforeException, SQLException;

    void insertFollow(String user, String other)
            throws NotSignedInException, AlreadyFollowingException, UserDoesNotExistException, SQLException;
    void deleteFollow(String user, String other)
            throws NotFollowedBeforeException, NotSignedInException, UserDoesNotExistException, SQLException;

    Iterator<String> selectAllUsers(String user)
            throws NotSignedInException, SQLException;
    Iterator<String> selectFollowedUsers(String user)
            throws NotSignedInException, SQLException;
    Iterator<String> selectFollowedPosts(String user)
            throws NotSignedInException, SQLException;
    Iterator<String> selectPostLikers(String user, String post_id)
            throws NotSignedInException, FeedNotExistException, SQLException;
    Iterator<String> selectPublicPosts(String other)
            throws UserDoesNotExistException, SQLException;
}
