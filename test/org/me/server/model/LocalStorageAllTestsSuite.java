package org.me.server.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.me.server.model.bl.TestUserBL_normal;
import org.me.server.model.dao.TestLocalStorageUserDao_SignedInUserOperations;
import org.me.server.model.dao.TestLocalStorageUserDao_BasicOperations;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestLocalStorageUserDao_BasicOperations.class,
        TestLocalStorageUserDao_SignedInUserOperations.class,
        TestUserBL_normal.class,
})

public class LocalStorageAllTestsSuite {
    // the class remains empty,
    // used only as a holder for the above annotations
}
