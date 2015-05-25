package org.me.server.model.bl;


import org.junit.Test;
import org.me.server.model.Exceptions.UsernameAlreadyExistsException;

import static org.junit.Assert.fail;

public class TestUserBl {
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
