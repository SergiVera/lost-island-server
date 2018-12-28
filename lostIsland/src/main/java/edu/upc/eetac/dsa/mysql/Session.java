package edu.upc.eetac.dsa.mysql;

import edu.upc.eetac.dsa.exception.UserNotFoundException;

import java.util.HashMap;
import java.util.List;

public interface Session<E> {
    void save(Object entity);
    void close();
    Object get(Class theClass, int ID);
    int getID(Class theClass, String username, String password) throws UserNotFoundException;
    boolean checkIfUserIsRegistered(Class theClass, String username, String password);
    void update(Object object, int ID);
    void delete(Object object, int ID);
    List<Object> findAll(Class theClass);
    List<Object> findAll(Class theClass, HashMap params);
    List<Object> query(String query, Class theClass, HashMap params);
}
