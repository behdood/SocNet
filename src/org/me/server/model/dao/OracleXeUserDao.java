package org.me.server.model.dao;

import org.me.server.model.Exceptions.*;
import org.me.server.model.dto.Post;
import org.me.server.model.dto.Privacy;

import java.sql.*;
import java.util.Iterator;
import java.util.Vector;


public class OracleXeUserDao implements UserDao {
    Connection con;
    Statement st;
    private final String users_tbl = "SocNetUsers";
    private final String posts_tbl = "SocNetPosts";
    private final String followers_tbl = "SocNetFollowTbl";
    private final String likers_tbl = "SocNetLikeTbl";


    public OracleXeUserDao(String className, String connectionURL, String username, String password) throws ClassNotFoundException, SQLException {
        Class.forName(className);
        con = DriverManager.getConnection(connectionURL, username, password);
        st = con.createStatement();
    }

    @Override
    public void signUp(String username, String password) throws UsernameAlreadyExistsException, SQLException {
        ResultSet r = st.executeQuery("select * from " + users_tbl + " where username = '" + username + "'");
        if (r.next())
            throw new UsernameAlreadyExistsException();
        st.executeUpdate("insert into " + users_tbl + " values ('" + username + "', '" + password + "')");

        // each person follows him/herself!
        st.executeUpdate("insert into " + followers_tbl + " values ('" + username + "', '" + username + "')");
        st.close();
        con.close();
    }

    @Override
    public void signIn(String username, String password) throws UsernameDoesNotExistException, IncorrectPasswordException, SQLException {
        ResultSet r;
        r = st.executeQuery("select * from " + users_tbl + " where username = '" + username + "'");
        if (!r.next())
            throw new UsernameDoesNotExistException();

        r = st.executeQuery("select * from " + users_tbl + " where username = '" + username + "' and password = '" + password + "'");
        if (!r.next())
            throw new IncorrectPasswordException();
        st.close();
        con.close();
    }

    @Override
    public void insertPost(String user, Post p) throws NotSignedInException, InvalidPostIdException, SQLException {
        String is_private;
        if (p.getPrivacy().equals(Privacy.PUBLIC))                is_private = "n";
        else                                                      is_private = "y";

        ResultSet r;
        r = st.executeQuery("select * from " + posts_tbl + " where post_id = '" + p.getId() + "'");
        if (r.next())
            throw new InvalidPostIdException();

        st.executeUpdate("insert into " + posts_tbl + " values ('" + p.getId() + "', '" + p.getOwner() + "', '"
        + p.getTime() + "', '" + is_private + "', '" + p.getText() + "')");
        st.close();
        con.close();

    }

    @Override
    public void deletePost(String user, String post_id) throws NotSignedInException, PostDoesNotExistException, SQLException {
        ResultSet r;
        r = st.executeQuery("select * from " + posts_tbl + " where post_id = '" + post_id + "'");
        if (!r.next())
            throw new PostDoesNotExistException();

        st.executeUpdate("delete from " + posts_tbl + " where post_id = '" + post_id + "'");
        st.close();
        con.close();

    }

    @Override
    public void insertLike(String user, String post_id) throws NotSignedInException, PostDoesNotExistException, AlreadyLikedException, SQLException {
        ResultSet r;
        r = st.executeQuery("select * from " + posts_tbl + " where post_id = '" + post_id + "'");
        if (!r.next())
            throw new PostDoesNotExistException();

        r = st.executeQuery("select * from " + likers_tbl + " where post_id = '" + post_id + "' and user_liker = '" + user + "'");
        if (r.next())
            throw new AlreadyLikedException(user);

        st.executeUpdate("insert into " + likers_tbl + " values ('" + post_id + "', '" + user + "')");
        st.close();
        con.close();

    }

    @Override
    public void deleteLike(String user, String post_id) throws NotSignedInException, PostDoesNotExistException, NotLikedBeforeException, SQLException {
        ResultSet r;
        r = st.executeQuery("select * from " + posts_tbl + " where post_id = '" + post_id + "'");
        if (!r.next())
            throw new PostDoesNotExistException();

        r = st.executeQuery("select * from " + likers_tbl + " where post_id = '" + post_id + "' and user_liker = '" + user + "'");
        if (!r.next())
            throw new NotLikedBeforeException(user);

        st.executeUpdate("delete from " + likers_tbl + " where post_id = '" + post_id + "' and user_liker = '" + user + "')");
        st.close();
        con.close();

    }

    @Override
    public void insertFollow(String user, String other) throws NotSignedInException, AlreadyFollowingException, UsernameDoesNotExistException, SQLException {
        ResultSet r;

        r = st.executeQuery("select * from " + users_tbl + " where username = '" + other + "'");
        if (!r.next())
            throw new UsernameDoesNotExistException(other);

        r = st.executeQuery("select * from " + followers_tbl + " where user_follower = '" + user + "' and user_followed = '" + other + "'");
        if (r.next())
            throw new AlreadyFollowingException(user, other);


        st.executeUpdate("insert into " + followers_tbl + " values ('" + user + "', '" + other + "')");
        st.close();
        con.close();

    }

    @Override
    public void deleteFollow(String user, String other) throws NotFollowingException, NotSignedInException, UsernameDoesNotExistException, SQLException {
        ResultSet r;

        r = st.executeQuery("select * from " + users_tbl + " where username = '" + other + "'");
        if (!r.next())
            throw new UsernameDoesNotExistException(other);

        r = st.executeQuery("select * from " + followers_tbl + " where user_follower = '" + user + "' and user_followed = '" + other + "'");
        if (!r.next())
            throw new NotFollowingException(user, other);

        st.executeUpdate("delete from " + followers_tbl + " where user_follower ='" + user + "' and user_followed = '" + other + "'");
        st.close();
        con.close();
    }

    @Override
    public Iterator<String> selectAllUsers(String user) throws NotSignedInException, SQLException {
        ResultSet r;
        Vector<String> tmp = new Vector<>();
        r = st.executeQuery("select username from " + users_tbl + " order by username");
        while (r.next())
            tmp.add(r.getString("username"));

        st.close();
        con.close();
        return tmp.iterator();
    }

    @Override
    public Iterator<String> selectFollowedUsers(String user) throws NotSignedInException, SQLException {
        ResultSet r;
        Vector<String> tmp = new Vector<>();
        r = st.executeQuery("select * from " + followers_tbl + " where user_follower = '" + user + "' order by user_followed");
        while (r.next())
            tmp.add(r.getString("user_followed"));
        st.close();
        con.close();
        return tmp.iterator();
    }

    @Override
    public Iterator<String> selectFollowedPosts(String user) throws NotSignedInException, SQLException {
        ResultSet r;

        r = st.executeQuery("select post_id, owner, time_stamp, text from " + followers_tbl + ", " +  posts_tbl + " where user_follower = '" + user
                + "' and user_followed = owner order by post_id desc");
        Vector<String> tmp = new Vector<>();
        Vector<String> tmp_ids = new Vector<>();
        while (r.next()) {
            tmp_ids.add(r.getString("post_id"));
            tmp.add("<" + r.getString("time_stamp") + "> " + r.getString("owner") + ": \"" + r.getString("text") + "\" ");
        }
        for (int i = 0; i< tmp_ids.size(); i++) {
            String n = getNumberLikes(tmp_ids.get(i));
            if (n.equals("1"))
                n += " like";
            else
                n += " likes";
            tmp.set(i, tmp.get(i) + n);
        }
        st.close();
        con.close();
        return tmp.iterator();
    }

    @Override
    public Iterator<String> selectPostLikers(String user, String post_id) throws NotSignedInException, PostDoesNotExistException, SQLException {
        ResultSet r;
        r = st.executeQuery("select * from " + posts_tbl + " where post_id = '" + post_id + "'");
        if (!r.next())
            throw new PostDoesNotExistException();

        Vector<String> tmp = new Vector<>();
        r = st.executeQuery("select * from " + likers_tbl + " where post_id = '" + post_id + "' order by user_liker");
        while (r.next())
            tmp.add(r.getString("user_liker"));
        st.close();
        con.close();
        return tmp.iterator();
    }

    @Override
    public Iterator<String> selectPublicPosts(String other) throws UsernameDoesNotExistException, SQLException {
        ResultSet r;
        r = st.executeQuery("select * from " + users_tbl + " where username = '" + other + "'");
        if (!r.next())
            throw new UsernameDoesNotExistException(other);

        r = st.executeQuery("select post_id, owner, time_stamp, text from " + posts_tbl + " where owner = '" + other + "' and is_private = 'n' order by post_id desc");
        Vector<String> tmp = new Vector<>();
        Vector<String> tmp_ids = new Vector<>();
        while (r.next()) {
            tmp_ids.add(r.getString("post_id"));
            tmp.add("<" + r.getString("time_stamp") + "> " + r.getString("owner") + ": \"" + r.getString("text") + "\" ");
        }
        for (int i = 0; i< tmp_ids.size(); i++) {
            String n = getNumberLikes(tmp_ids.get(i));
            if (n.equals("1"))
                n += " like";
            else
                n += " likes";
            tmp.set(i, tmp.get(i) + n);
        }
        st.close();
        con.close();
        return tmp.iterator();
    }

    private String getNumberLikes(String post_id) throws SQLException {
        ResultSet r2;
        r2 = st.executeQuery("select count(*) as n from " + likers_tbl + " where post_id = '" + post_id + "'");
        if (r2.next())
            return r2.getString("n");
        return "0";
    }
}
