package org.me.server.model.databases;


import java.util.Collection;
import java.util.Map;

public class GenericDB<T> {
    private Map<String, T> db;

    Collection<String> s;

    public GenericDB(Map<String, T> db) {
        this.db = db;


    }

//    public String addElement
}
