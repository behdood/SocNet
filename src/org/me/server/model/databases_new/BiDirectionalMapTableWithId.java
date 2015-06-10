package org.me.server.model.databases_new;

import org.me.server.model.dto.Id;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class BiDirectionalMapTableWithId<T> implements BaseTableWithId<T> {

    private Map<Id, T> table;
    private Map<T, Id> reverse_table;

    public BiDirectionalMapTableWithId() {
        table = initializeTable();
        reverse_table = initializeTable();
    }

    @Override
    public boolean containsKey(Id key) {
        return table.containsKey(key);
    }

    @Override
    public boolean containsValue(T value) {
        return reverse_table.containsKey(value);
    }

    @Override
    public boolean containsPair(Id key, T value) {
        throw new RuntimeException("NOT IMPLEMENTED!");
    }

    @Override
    public boolean insert(Id key, T value) {
        table.put(key, value);
        reverse_table.put(value, key);
        return true;
    }

    @Override
    public boolean delete(Id key) {
        return false;
    }

    @Override
    public boolean delete(T value) {
        return false;
    }

    @Override
    public boolean delete(Id key, T value) {
        return false;
    }

//    @Override
//    public void insert(T value) {
//
//    }

    @Override
    public T getValue(Id key) {
        return null;
    }

    protected ConcurrentHashMap initializeTable() {
        return new ConcurrentHashMap<>();
    }

}
