package edu.upc.eetac.dsa.mysql;

import edu.upc.eetac.dsa.exception.UserNotFoundException;
import org.apache.commons.collections.map.MultiValueMap;

import java.util.HashMap;
import java.util.List;

public interface Session<E> {
    void save(Object entity);
    void customSave(Object entity);
    void close();
    Object get(Class theClass, int ID);
    int getID(Class theClass, String username, String password) throws UserNotFoundException;
    boolean checkIfUserIsRegistered(Class theClass, String username, String password);
    void update(Object object, int ID);
    void delete(Object object, int ID);
    void delete(Object object, HashMap params);
    List<Object> findAll(Class theClass);
    List<Object> findAll(Class theClass, HashMap params);
    List<Object> query(String query, Class theClass, MultiValueMap params);
    Object singleQuery(String query, Class theClass, MultiValueMap params);
}
