package org.me.server.model.dao;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.me.server.model.Exceptions.IncorrectPasswordException;
import org.me.server.model.Exceptions.UsernameAlreadyExistsException;
import org.me.server.model.Exceptions.UserDoesNotExistException;
import org.me.server.model.databases.local.LocalDatabaseHandler;
import org.me.server.model.dto.Id;
import org.me.server.model.dto.User;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

public class TestLocalStorageUserDao_BasicOperations {
    UserDao userDao;

    @Before
    public void setUp() throws Exception {
        LocalDatabaseHandler dbHandler = new LocalDatabaseHandler(true);
        userDao = new LocalStorageUserDao(dbHandler);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createUserAndSignIn() throws Exception {
        User temp_user = getFakeUser();

        userDao.createUser(temp_user);

        assertTrue(userDao.existUser(temp_user));


        Id returnedId = userDao.signInUser(temp_user);

        assertUserIdIsValid(returnedId);
        assertTrue(userDao.existUser(returnedId));
    }

    @Test(expected = UsernameAlreadyExistsException.class)
    public void createUser_duplicateThrowsException() throws Exception {
        User temp_user = getFakeUser();
        User temp_user_duplicate = getFakeUser();

        userDao.createUser(temp_user);
        userDao.createUser(temp_user_duplicate);
    }

    @Test(expected = UserDoesNotExistException.class)
    public void signInUser_userNotExistThrowsException() throws Exception {
        User non_existent_user = getFakeUser("non", "existent");

        assertFalse(userDao.existUser(non_existent_user));

        userDao.signInUser(non_existent_user);
    }

    @Test(expected = IncorrectPasswordException.class)
    public void signInUser_incorrectPasswordThrowsException() throws Exception {
        User temp_user = getFakeUser("temp_user", "temp_pass");
        User temp_user_modified = getFakeUser("temp_user", "changed");

        userDao.createUser(temp_user);

        userDao.signInUser(temp_user_modified);
    }

    @Test
    public void deleteUser() throws Exception {
        User temp_user = getFakeUser();
        userDao.createUser(temp_user);
        Id returnedId = userDao.signInUser(temp_user);

        userDao.deleteUser(returnedId);

        assertFalse(getTempDbHandler().containsUser(returnedId));
        assertFalse(getTempDbHandler().containsUser(new User(temp_user.getUsername(), "")));
    }
    @Test(expected = UserDoesNotExistException.class)
    public void deleteUser_notExistsThrowsException() throws Exception {
        Id non_existent_id = new Id("-2");
        userDao.deleteUser(non_existent_id);
    }

    private void assertUserIdIsValid(Id userId) {
        assertNotNull(userId);
        assertThat(userId.getValue(), not(equalTo("")));
    }

    private User getFakeUser() {
        return getFakeUser("temp_user", "temp_pass");
    }
    private User getFakeUser(String username, String password) {
        return new User(username, password);
    }

    private LocalDatabaseHandler getTempDbHandler() {
        return new LocalDatabaseHandler();
    }
}
