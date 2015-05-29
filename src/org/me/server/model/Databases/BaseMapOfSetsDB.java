package org.me.server.model.databases;


import javafx.util.Pair;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class BaseMapOfSetsDB /*implements BaseDB*/{
//    Set<Pair<String, String>> db;
    Map<String, Set<String>> db;













    public BaseMapOfSetsDB() {
        this.db = new ConcurrentHashMap<>();
    }

//    public boolean insertFirstElement(String firstElement) {
//        if (containsFirstElement(firstElement))
//            return false;
//
//        db.put(firstElement, new HashSet<String>());
//        return true;
//    }
//
//    public boolean insertPair(String firstElement, String secondElement) {
//        if (containsPair(firstElement, secondElement))
//            return false;
//
//        if (!containsFirstElement(firstElement))
//            insertFirstElement(firstElement);
//
//        db.put(firstElement, db.get(firstElement).add(secondElement));
//
//    }
//
//    public boolean containsFirstElement(String firstElement) {
//        return db.containsKey(firstElement);
//    }
//
//    public boolean containsPair(String firstElement, String secondElement) {
//        return containsFirstElement(firstElement) && db.get(firstElement).contains(secondElement);
//    }


}
