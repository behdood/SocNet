package org.me.server.model.databases_new;


import org.me.server.model.dto.Id;

public interface BaseTableWithId<T> {

    boolean containsKey(Id key);

    boolean containsValue(T value);

    boolean containsPair(Id key, T value);

    boolean insert(Id key, T value);

    boolean delete(Id key);

    boolean delete(T value);

    boolean delete(Id key, T value);

    Object getValue(Id key);

}
