package org.me.server.model.databases_new;


import org.me.server.model.dto.Feed;
import org.me.server.model.dto.Id;
import org.me.server.model.dto.User;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class LocalDatabase {
    public Map<Id, User> USERS_TABLE;
    public Map<User, Id> USERS_TABLE_REVERSED;
    public Map<Id, Feed> FEEDS_TABLE;
    public Map<Id, Set<Id>> USER_OWN_FEEDS_TABLE;
    public Map<Id, Set<Id>> USER_FOLLOWS_USERS_TABLE;
    public Map<Id, Set<Id>> FEED_LIKED_BY_USER_TABLE;

//    public BiDirectionalMapTableWithId<User> USERS_TABLE;
//    public BiDirectionalMapTableWithId<Feed> FEEDS_TABLE;
//
//    public MapOfSetsTableWithId USER_OWN_FEEDS_TABLE;
//    public MapOfSetsTableWithId USER_FOLLOWS_USERS_TABLE;
//    public MapOfSetsTableWithId FEED_LIKED_BY_USER_TABLE;

    private static LocalDatabase instance = null;

    private LocalDatabase() {
//        USERS_TABLE = new BiDirectionalMapTableWithId<>();
//        FEEDS_TABLE = new BiDirectionalMapTableWithId<>();
//
//        USER_OWN_FEEDS_TABLE = new MapOfSetsTableWithId();
//        USER_FOLLOWS_USERS_TABLE = new MapOfSetsTableWithId();
//        FEED_LIKED_BY_USER_TABLE = new MapOfSetsTableWithId();

        USERS_TABLE = new ConcurrentHashMap<>();
        USERS_TABLE_REVERSED = new ConcurrentHashMap<>();
        FEEDS_TABLE = new ConcurrentHashMap<>();
        USER_OWN_FEEDS_TABLE = new ConcurrentHashMap<>();
        USER_FOLLOWS_USERS_TABLE = new ConcurrentHashMap<>();
        FEED_LIKED_BY_USER_TABLE = new ConcurrentHashMap<>();
    }

    public synchronized static LocalDatabase getInstance(boolean resetDatabase) {
        if (instance == null || resetDatabase)
            instance = new LocalDatabase();

        return instance;
    }

    public Set<Id> getNewIdSet() {
        return new HashSet<>();
    }

//    public void resetDatabase() {
//        USERS_TABLE.clear();
//        USERS_TABLE_REVERSED.clear();
//        USER_FOLLOWS_USERS_TABLE.clear();
//        USER_OWN_FEEDS_TABLE.clear();
//        FEED_LIKED_BY_USER_TABLE.clear();
//    }


}
