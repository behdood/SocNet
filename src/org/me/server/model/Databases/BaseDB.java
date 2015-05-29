package org.me.server.model.databases;


import org.me.server.model.Exceptions.database.BaseDatabaseExceptions;

public interface BaseDB<T1, T2> {

    void addPair(T1 firstElement, T2 secondElement) throws BaseDatabaseExceptions;

    void removePair(T1 firstElement, T2 secondElement);

    void removePairByFirstElement(T1 firstElement);

    boolean containsPair(T1 firstElement, T2 secondElement);

    void addPairByFirstElement(T1 firstElement, T2 secondElement);

    T2 getSecondByFirstElement(T1 firstElement);

    T1 getFirstBySecondElement(T2 secondElement);

}
