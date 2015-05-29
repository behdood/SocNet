package org.me.server.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.me.server.model.bl.TestUserBL_normal;
import org.me.server.model.bl.TestUserBL_NotLogged;
import org.me.server.model.dao.TestLocalStorageUserDao;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestLocalStorageUserDao.class,
        TestUserBL_normal.class,
        TestUserBL_NotLogged.class
})

public class LocalStorageAllTestsSuite {
    // the class remains empty,
    // used only as a holder for the above annotations
}
