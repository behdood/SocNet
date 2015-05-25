package org.me.server.model.dao;

import org.me.server.model.dto.Post;
import org.me.server.model.Exceptions.*;

import java.sql.SQLException;
import java.util.Iterator;


public interface UserDao {
    void signUp(String username, String password)
            throws UsernameAlreadyExistsException, SQLException;
    void signIn(String username, String password)
            throws UsernameDoesNotExistException, IncorrectPasswordException, SQLException;

    void insertPost(String user, Post p)
            throws NotSignedInException, InvalidPostIdException, SQLException;
    void deletePost(String user, String post_id)
            throws NotSignedInException, PostDoesNotExistException, SQLException;

    void insertLike(String user, String post_id)
            throws NotSignedInException, PostDoesNotExistException, AlreadyLikedException, SQLException;
    void deleteLike(String user, String post_id)
            throws NotSignedInException, PostDoesNotExistException, NotLikedBeforeException, SQLException;

    void insertFollow(String user, String other)
            throws NotSignedInException, AlreadyFollowingException, UsernameDoesNotExistException, SQLException;
    void deleteFollow(String user, String other)
            throws NotFollowingException, NotSignedInException, UsernameDoesNotExistException, SQLException;

    Iterator<String> selectAllUsers(String user)
            throws NotSignedInException, SQLException;
    Iterator<String> selectFollowedUsers(String user)
            throws NotSignedInException, SQLException;
    Iterator<String> selectFollowedPosts(String user)
            throws NotSignedInException, SQLException;
    Iterator<String> selectPostLikers(String user, String post_id)
            throws NotSignedInException, PostDoesNotExistException, SQLException;
    Iterator<String> selectPublicPosts(String other)
            throws UsernameDoesNotExistException, SQLException;
}
