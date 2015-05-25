package org.me.server.model.Databases;

import org.me.server.model.dto.Post;
import org.me.server.model.dto.User;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;


public class FileDatabase extends Database implements Serializable {

    private static org.me.server.model.Databases.FileDatabase instance;

    public Map<String, User> allUsers;
    public Map<String, Post> allPosts;

    public Map<String, Vector<String>> followerMap;
    public Map<String, Vector<String>> postLikerMap;

    private FileDatabase() {
        allUsers = new ConcurrentSkipListMap<>();
        allPosts = new ConcurrentSkipListMap<>();

        followerMap = new ConcurrentHashMap<>();
        postLikerMap = new ConcurrentHashMap<>();
    }

    static public synchronized org.me.server.model.Databases.FileDatabase getInstance() {
        if (instance == null) {
            instance = new org.me.server.model.Databases.FileDatabase();
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
