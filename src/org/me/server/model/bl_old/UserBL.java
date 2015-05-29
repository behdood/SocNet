package org.me.server.model.bl_old;

import org.me.server.model.databases.Database;
import org.me.server.model.Exceptions.*;
import org.me.server.model.dao_old.UserDAOFactory;
import org.me.server.model.dto_old.Feed;

import java.sql.SQLException;
import java.util.Iterator;


public class UserBL implements UserIX {

    Database db;

    public UserBL(Database db) {
        this.db = db;
    }

    @Override
    public ActionResult register(String username, String password) {
        try {
            org.me.server.model.dao_old.UserDao userDAO = UserDAOFactory.getUserDAO(db);
            userDAO.signUp(username, password);
            return ActionResult.OK;
        } catch (SQLException e) {
            return ActionResult.DATABASE_CONNECTION_ERROR;
        } catch (ClassNotFoundException e) {
            return ActionResult.DATABASE_CONNECTION_ERROR;
        } catch (UsernameAlreadyExistsException e) {
            return ActionResult.USERNAME_EXIST_ERROR;
        }
    }

    @Override
    public ActionResult login(String username, String password) {
        try {
            org.me.server.model.dao_old.UserDao userDAO = UserDAOFactory.getUserDAO(db);
            userDAO.signIn(username, password);
            return ActionResult.OK;
        } catch (ClassNotFoundException e) {
            return ActionResult.DATABASE_CONNECTION_ERROR;
        } catch (SQLException e) {
            return ActionResult.DATABASE_CONNECTION_ERROR;
        } catch (UsernameDoesNotExistException e) {
            return ActionResult.USERNAME_NOT_EXIST_ERROR;
        } catch (IncorrectPasswordException e) {
            return ActionResult.INCORRECT_PASSWORD_ERROR;
        }
    }

    @Override
    public ActionResult logout() {
        return ActionResult.OK;
    }


    @Override
    public Iterator<String> getAllUsers(String username) {
        if (username.equals(""))
            return null;
        try {
            org.me.server.model.dao_old.UserDao userDAO = UserDAOFactory.getUserDAO(db);
            return userDAO.selectAllUsers(username);
        } catch (NotSignedInException | SQLException | ClassNotFoundException e) {
            return null;
        }
    }

    @Override
    public Iterator<String> getFollowedUsers(String username) {
        if (username.equals(""))
            return null;
        try {
            org.me.server.model.dao_old.UserDao userDAO = UserDAOFactory.getUserDAO(db);
            return userDAO.selectFollowedUsers(username);
        } catch (NotSignedInException | SQLException | ClassNotFoundException e) {
            return null;
        }
    }

    @Override
    public Iterator<String> getFollowedFeeds(String username) {
        if (username.equals(""))
            return null;
        try {
            org.me.server.model.dao_old.UserDao userDAO = UserDAOFactory.getUserDAO(db);
            return userDAO.selectFollowedPosts(username);
        } catch (NotSignedInException | SQLException | ClassNotFoundException e) {
            return null;
        }
    }

    @Override
    public Iterator<String> getPostLikers(String username, String post_id) {
        if (username.equals(""))
            return null;
        try {
            org.me.server.model.dao_old.UserDao userDAO = UserDAOFactory.getUserDAO(db);
            return  userDAO.selectPostLikers(username, post_id);
        } catch (NotSignedInException | PostDoesNotExistException | SQLException | ClassNotFoundException e) {
            return null;
        }
    }

    @Override
    public Iterator<String> getPublicFeeds(String otherUsername) {
        try {
            org.me.server.model.dao_old.UserDao userDAO = UserDAOFactory.getUserDAO(db);
            return userDAO.selectPublicPosts(otherUsername);
        } catch (UsernameDoesNotExistException | SQLException | ClassNotFoundException e) {
            return null;
        }
    }


    private ActionResult addFeed(String username, String message, String time_stamp,
                                 String post_id, boolean is_public) {
        if (username.equals(""))
            return ActionResult.NOT_SIGNED_IN_ERROR;
        Feed feed = new Feed(username, message, time_stamp, post_id, is_public);
        try {
            org.me.server.model.dao_old.UserDao userDAO = UserDAOFactory.getUserDAO(db);
            userDAO.insertPost(username, feed);
            return ActionResult.OK;
        } catch (NotSignedInException e) {
            return ActionResult.NOT_SIGNED_IN_ERROR;
        } catch (InvalidPostIdException e) {
            return ActionResult.INVALID_POST_ID_ERROR;
        } catch (ClassNotFoundException e) {
            return ActionResult.DATABASE_CONNECTION_ERROR;
        } catch (SQLException e) {
            return ActionResult.DATABASE_CONNECTION_ERROR;
        }
    }

    @Override
    public ActionResult sharePublic(String username, String message, String time_stamp, String post_id) {
        return addFeed(username, message, time_stamp, post_id, true);
    }

    @Override
    public ActionResult updateStatus(String username, String message, String time_stamp, String post_id) {
        return addFeed(username, message, time_stamp, post_id, false);
    }

    @Override
    public ActionResult deleteStatus(String username, String post_id) {
        if (username.equals(""))
            return ActionResult.NOT_SIGNED_IN_ERROR;
        try {
            org.me.server.model.dao_old.UserDao userDAO = UserDAOFactory.getUserDAO(db);
            userDAO.deletePost(username, post_id);
            return ActionResult.OK;
        } catch (NotSignedInException e) {
            return ActionResult.NOT_SIGNED_IN_ERROR;
        } catch (PostDoesNotExistException e) {
            return ActionResult.POST_DOES_NOT_EXIST_ERROR;
        } catch (ClassNotFoundException e) {
            return ActionResult.DATABASE_CONNECTION_ERROR;
        } catch (SQLException e) {
            return ActionResult.DATABASE_CONNECTION_ERROR;
        }
    }


    @Override
    public ActionResult follow(String username, String otherUsername) {
        if (username.equals(""))
            return ActionResult.NOT_SIGNED_IN_ERROR;
        try {
            org.me.server.model.dao_old.UserDao userDAO = UserDAOFactory.getUserDAO(db);
            userDAO.insertFollow(username, otherUsername);
            return ActionResult.OK;
        } catch (NotSignedInException e) {
            return ActionResult.NOT_SIGNED_IN_ERROR;
        } catch (AlreadyFollowingException e) {
            return ActionResult.ALREADY_FOLLOWING_ERROR;
        } catch (UsernameDoesNotExistException e) {
            return ActionResult.USERNAME_NOT_EXIST_ERROR;
        } catch (ClassNotFoundException e) {
            return ActionResult.DATABASE_CONNECTION_ERROR;
        } catch (SQLException e) {
            return ActionResult.DATABASE_CONNECTION_ERROR;
        }
    }

    @Override
    public ActionResult unfollow(String username, String otherUsername) {
        if (username.equals(""))
            return ActionResult.NOT_SIGNED_IN_ERROR;
        try {
            org.me.server.model.dao_old.UserDao userDAO = UserDAOFactory.getUserDAO(db);
            userDAO.deleteFollow(username, otherUsername);
            return ActionResult.OK;
        } catch (NotSignedInException e) {
            return ActionResult.NOT_SIGNED_IN_ERROR;
        } catch (NotFollowingException e) {
            return ActionResult.ALREADY_NOT_FOLLOWING_ERROR;
        } catch (UsernameDoesNotExistException e) {
            return ActionResult.USERNAME_NOT_EXIST_ERROR;
        } catch (ClassNotFoundException e) {
            return ActionResult.DATABASE_CONNECTION_ERROR;
        } catch (SQLException e) {
            return ActionResult.DATABASE_CONNECTION_ERROR;
        }
    }

    @Override
    public ActionResult like(String username, String post_id) {
        if (username.equals(""))
            return ActionResult.NOT_SIGNED_IN_ERROR;
        try {
            org.me.server.model.dao_old.UserDao userDAO = UserDAOFactory.getUserDAO(db);
            userDAO.insertLike(username, post_id);
            return ActionResult.OK;
        } catch (NotSignedInException e) {
            return ActionResult.NOT_SIGNED_IN_ERROR;
        } catch (PostDoesNotExistException e) {
            return ActionResult.POST_DOES_NOT_EXIST_ERROR;
        } catch (AlreadyLikedException e) {
            return ActionResult.ALREADY_LIKED_ERROR;
        } catch (ClassNotFoundException e) {
            return ActionResult.DATABASE_CONNECTION_ERROR;
        } catch (SQLException e) {
            return ActionResult.DATABASE_CONNECTION_ERROR;
        }
    }

    @Override
    public ActionResult unlike(String username, String post_id) {
        if (username.equals(""))
            return ActionResult.NOT_SIGNED_IN_ERROR;
        try {
            org.me.server.model.dao_old.UserDao userDAO = UserDAOFactory.getUserDAO(db);
            userDAO.deleteLike(username, post_id);
            return ActionResult.OK;
        } catch (NotSignedInException e) {
            return ActionResult.NOT_SIGNED_IN_ERROR;
        } catch (PostDoesNotExistException e) {
            return ActionResult.POST_DOES_NOT_EXIST_ERROR;
        } catch (NotLikedBeforeException e) {
            return ActionResult.ALREADY_NOT_LIKED_ERROR;
        } catch (ClassNotFoundException e) {
            return ActionResult.DATABASE_CONNECTION_ERROR;
        } catch (SQLException e) {
            return ActionResult.DATABASE_CONNECTION_ERROR;
        }
    }

}

