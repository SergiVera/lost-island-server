package edu.upc.eetac.dsa.mysql;

import edu.upc.eetac.dsa.exception.UserNotFoundException;
import edu.upc.eetac.dsa.util.ObjectHelper;
import edu.upc.eetac.dsa.util.QueryHelper;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SessionImpl implements Session {
    final static Logger log = Logger.getLogger(ProductManagerImpl.class.getName());

    private final Connection conn;

    public SessionImpl(Connection conn) {
        this.conn = conn;
    }

    public void save(Object entity) {

        String insertQuery = QueryHelper.createQueryINSERT(entity);

        PreparedStatement pstm;


        try {
            pstm = conn.prepareStatement(insertQuery);
            pstm.setObject(1,0);
            int i = 2;


            for (String field : ObjectHelper.getFields(entity, false)) {
                pstm.setObject(i++, ObjectHelper.getter(entity, field));
            }


            pstm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public void customSave(Object entity, boolean noclass) {

        String insertQuery = QueryHelper.createQueryCUSTOMINSERT(entity, noclass);

        PreparedStatement pstm;


        try {
            pstm = conn.prepareStatement(insertQuery);
            int i = 1;

            if(noclass == false) {
                for (String field : ObjectHelper.getFields(entity, false)) {
                    pstm.setObject(i++, ObjectHelper.getter(entity, field));
                }
            }
            else{
                for (String field : ObjectHelper.getFields(entity, true)) {
                    pstm.setObject(i++, ObjectHelper.getter(entity, field));
                }
            }

            pstm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public void close() {
        try {
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getFieldName(int i, ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        String name = rsmd.getColumnName(i);

        System.out.println("columna "+i+" name: "+name);
        return name;
    }

    public Object get(Class theClass, int ID) {
        String selectQuery = QueryHelper.createQuerySELECT2(theClass);

        Object entity = null;
        try {
            entity = theClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        ResultSet rs;
        PreparedStatement pstm;

        try {
            pstm = conn.prepareStatement(selectQuery);
            pstm.setObject(1,ID);
            rs = pstm.executeQuery();


            while (rs.next()) {
                Field[] fields = theClass.getDeclaredFields();
                rs.getString(1);
                for (int i = 0; i < fields.length; i++) {


                    String fieldName = this.getFieldName(i + 2, rs);

                    ObjectHelper.setter(entity, fieldName, rs.getObject(i + 2));
                }

            }



        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return entity;
    }

    public int getID(Class theClass, String username, String password) throws UserNotFoundException {
        String selectQuery = QueryHelper.createQuerySELECTIDUSER(theClass);

        ResultSet rs;
        PreparedStatement pstm;

        int id;

        try {
            pstm = conn.prepareStatement(selectQuery);
            pstm.setObject(1, username);
            pstm.setObject(2, password);
            rs = pstm.executeQuery();

            rs.next();

            id = rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserNotFoundException();
        }

        return id;
    }

    public boolean checkIfUserIsRegistered(Class theClass, String username, String password){
        String selectQuery = QueryHelper.createQueryCHECKUSER(theClass);

        ResultSet rs;
        PreparedStatement pstm;

        boolean empty = true;

        try {
            pstm = conn.prepareStatement(selectQuery);
            pstm.setObject(1, username);
            rs = pstm.executeQuery();

            while(rs.next()) {
                empty = false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(empty == true)
            return false;
        else
            return true;
    }

    public void update(Object object, int ID, boolean noclass) {
        String updateQuery = QueryHelper.createQueryUPDATE(object);

        PreparedStatement pstm;

        try {
            pstm = conn.prepareStatement(updateQuery);
            int i = 1;

            if(noclass == false) {
                for (String field : ObjectHelper.getFields(object, false)) {
                    pstm.setObject(i++, ObjectHelper.getter(object, field));
                }
            }
            else{
                for (String field : ObjectHelper.getFields(object, true)) {
                    pstm.setObject(i++, ObjectHelper.getter(object, field));
                }
            }

            pstm.setObject(i,ID);

            pstm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void delete(Object object, int ID) {
        String deleteQuery = QueryHelper.createQueryDELETE(object);

        PreparedStatement pstm;

        try {
            pstm = conn.prepareStatement(deleteQuery);
            pstm.setObject(1, ID);

            pstm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void delete(Object object, HashMap params) {
        String deleteQuery = QueryHelper.createQueryDELETE(object, params);

        PreparedStatement pstm;

        try {
            pstm = conn.prepareStatement(deleteQuery);

            pstm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<Object> findAll(Class theClass) {
        String findAllQuery = QueryHelper.findAllQuery(theClass);

        Object entity = null;
        List<Object> listOfObjects = new ArrayList<>();

        try {
            entity = theClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        ResultSet rs;
        PreparedStatement pstm;

        try {
            pstm = conn.prepareStatement(findAllQuery);
            rs = pstm.executeQuery();

            while(rs.next()){
                Field[] fields = theClass.getDeclaredFields();
                rs.getString(1);
                for (int i = 0; i<fields.length; i++){
                    ObjectHelper.setter(entity, fields[i].getName(), rs.getObject(i + 2));
                }

                listOfObjects.add(entity);

                entity = theClass.newInstance();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return listOfObjects;
    }

    public List<Object> findAll(Class theClass, HashMap params) {

        String findAllQuery = QueryHelper.findAllQuery(theClass, params);

        Object entity = null;
        List<Object> listOfObjects = new ArrayList<>();

        try {
            entity = theClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        ResultSet rs;
        PreparedStatement pstm;

        try {
            pstm = conn.prepareStatement(findAllQuery);
            rs = pstm.executeQuery();

            while(rs.next()){
                Field[] fields = theClass.getSuperclass().getDeclaredFields();
                rs.getString(1);
                for (int i = 0; i<fields.length; i++){
                    ObjectHelper.setter(entity, fields[i].getName(), rs.getObject(i + 2));
                }

                listOfObjects.add(entity);

                entity = theClass.newInstance();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return listOfObjects;
    }

    public List<Object> query(String query, Class theClass, MultiValueMap params) {
        String findAllQuery = QueryHelper.findAllQuery(query, params);

        Object entity = null;
        List<Object> listOfObjects = new ArrayList<>();

        try {
            entity = theClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        ResultSet rs;
        PreparedStatement pstm;

        try {
            pstm = conn.prepareStatement(findAllQuery);
            rs = pstm.executeQuery();

            while(rs.next()){
                Field[] fields = theClass.getSuperclass().getDeclaredFields();
                rs.getString(1);
                for (int i = 0; i<fields.length; i++){
                    log.info(" name "+fields[i].getName()+ " value " + rs.getObject(i + 1));
                    ObjectHelper.setter(entity, fields[i].getName(), rs.getObject(i + 1));
                }

                listOfObjects.add(entity);

                entity = theClass.newInstance();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return listOfObjects;
    }

    public Object singleQuery(String query, Class theClass, MultiValueMap params) {
        String findAllQuery = QueryHelper.findAllQuery(query, params);

        Object entity = null;

        try {
            entity = theClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        ResultSet rs;
        PreparedStatement pstm;

        try {
            pstm = conn.prepareStatement(findAllQuery);
            rs = pstm.executeQuery();

            while(rs.next()){
                Field[] fields = theClass.getSuperclass().getDeclaredFields();
                rs.getString(1);
                for (int i = 0; i<fields.length; i++){
                    log.info(" name "+fields[i].getName()+ " value " + rs.getObject(i + 1));
                    ObjectHelper.setter(entity, fields[i].getName(), rs.getObject(i + 1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return entity;
    }

    @Override
    public String customQuery(String query) {
        ResultSet rs;
        PreparedStatement pstm;

        String result = null;

        try {
            pstm = conn.prepareStatement(query);
            rs = pstm.executeQuery();

            rs.next();

            result = rs.getString(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
