package org.me.server.model.databases;


import org.me.server.model.Exceptions.database.BaseDatabaseExceptions;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class BaseMapDB<T1, T2> implements BaseDB<T1, T2>{
    private Map<T1, T2> db;


    public BaseMapDB() {
        this.db = new ConcurrentSkipListMap<>();
    }


    @Override
    public void addPair(T1 firstElement, T2 secondElement) throws BaseDatabaseExceptions {
        // no check for duplication
        db.put(firstElement, secondElement);


    }

    @Override
    public void removePair(T1 firstElement, T2 secondElement) {
        // uses a linear search, thus has high time-complexity

//        T1 found_elem
        for (Map.Entry<T1, T2> entry : db.entrySet()) {
//            if
            System.out.println(entry.getKey() + "/" + entry.getValue());
        }

        Iterator iterator = db.keySet().iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();

        }
        
//        return false;
    }

    @Override
    public boolean containsPair(T1 firstElement, T2 secondElement) {
        return false;
    }

    @Override
    public void addPairByFirstElement(T1 firstElement, T2 secondElement) {
//        return false;
    }

    @Override
    public void removePairByFirstElement(T1 firstElement) {
//        return false;
    }

    @Override
    public T2 getSecondByFirstElement(T1 firstElement) {
//        return db.get(firstElement);
            return null;
    }

    @Override
    public T1 getFirstBySecondElement(T2 secondElement) {
        return null;
    }


    //
//    public T getElementById(String id) {
//        long id_long = Long.parseLong(id);
//        return db.get(id);
//    }
//
//
//    public String insertElementAndReturnId(T element){
//        if (db.containsValue(element))
//            return "";
//        ++id;
//        String id_str = id +"";
//        db.put(id_str, element);
//        return id_str;
//    }
//
//    public boolean removeFromDb(String id) {
//        if (!db.containsKey(id))
//            return false;
//        db.remove(id);
//        return true;
//    }

}
