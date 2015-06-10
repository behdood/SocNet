package org.me.server.model.databases_old;

import org.me.server.model.dto_old.Feed;
import org.me.server.model.dto_old.User;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;


public class FileDatabase extends Database implements Serializable {

    private static FileDatabase instance;

    public Map<String, User> allUsers;
    public Map<String, Feed> allPosts;

    public Map<String, Vector<String>> followerMap;
    public Map<String, Vector<String>> postLikerMap;

    private FileDatabase() {
        allUsers = new ConcurrentSkipListMap<>();
        allPosts = new ConcurrentSkipListMap<>();

        followerMap = new ConcurrentHashMap<>();
        postLikerMap = new ConcurrentHashMap<>();
    }

    static public synchronized FileDatabase getInstance() {
        if (instance == null) {
            instance = new FileDatabase();
        }
        return instance;
    }

    private void readObjectNoData() throws ObjectStreamException {}
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        instance = this;
    }

    private Object readResolve() throws ObjectStreamException {
        return getInstance();
    }



}
