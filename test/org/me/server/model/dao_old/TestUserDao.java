package org.me.server.model.dao_old;


import org.junit.Test;
import org.me.server.model.Exceptions.UsernameAlreadyExistsException;

import static org.junit.Assert.fail;

public abstract class TestUserDao {

    @Test
    public void registerUser() throws Exception {
        fail("not implemented yet!");
    }

    @Test(expected = UsernameAlreadyExistsException.class)
    public void registerUser_duplicateThrowsException() throws Exception {
        fail("not implemented yet!");
    }

    @Test
    public void loginUser() throws Exception {



    }

    @org.junit.Test
    public void testAddPost() {


    }
}