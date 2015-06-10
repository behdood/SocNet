package org.me.server.model.databases_new;

import org.me.server.model.dto.Id;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class MapOfSetsTableWithId implements BaseTableWithId<Id> {

    Map<Id, Set<Id>> table;

    public MapOfSetsTableWithId() {
        table = initializeTable();
    }

    @Override
    public boolean containsKey(Id key) {
        return table.containsKey(key);
    }

    @Override
    public boolean containsValue(Id value) {
        throw new RuntimeException("NOT IMPLEMENTED!");
    }

    @Override
    public boolean containsPair(Id key, Id value) {
        throw new RuntimeException("NOT IMPLEMENTED!");
    }

    @Override
    public boolean insert(Id key, Id value) {
        return false;
    }

    @Override
    public boolean delete(Id value) {
        return false;
    }

    @Override
    public boolean delete(Id key, Id value) {
        return false;
    }

    @Override
    public Set<Id> getValue(Id key) {
        return null;
    }


    protected ConcurrentHashMap<Id, Set<Id>> initializeTable() {
        return new ConcurrentHashMap<>();
    }

    protected Set<Id> initializeSet() {
        return new HashSet<>();
    }
}
