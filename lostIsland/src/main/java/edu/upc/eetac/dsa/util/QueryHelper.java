package edu.upc.eetac.dsa.util;

import edu.upc.eetac.dsa.mysql.Condition;
import org.apache.commons.collections.map.MultiValueMap;

import java.util.*;

public class QueryHelper {

    //INSERT query
    public static String createQueryINSERT(Object entity) {

        StringBuffer sb = new StringBuffer("INSERT INTO ");
        sb.append(entity.getClass().getSimpleName()).append(" ");
        sb.append("(");

        String [] fields = ObjectHelper.getFields(entity, false);

        sb.append("ID");
        for (String field: fields) {
            sb.append(", ").append(field);
        }

        sb.append(") VALUES (?");

        for (String field: fields) {
            sb.append(", ?");
        }

        sb.append(")");

        return sb.toString();
    }

    //INSERT query with custom fields
    public static String createQueryCUSTOMINSERT(Object entity, boolean noclass){

        StringBuffer sb = new StringBuffer("INSERT INTO ");

        String[] fields;

        if(noclass == false) {
            sb.append(entity.getClass().getSimpleName()).append(" ");
            sb.append("(");

            fields = ObjectHelper.getFields(entity, false);
        }
        else{
            sb.append(entity.getClass().getSuperclass().getSimpleName()).append(" ");
            sb.append("(");

            fields = ObjectHelper.getFields(entity, true);
        }

        for (String field: fields) {
            sb.append(field).append(", ");
        }

        sb.delete(sb.length() -2, sb.length());

        sb.append(") VALUES (");

        for (String field: fields) {
            sb.append("? , ");
        }

        sb.delete(sb.length() -2, sb.length());

        sb.append(")");

        return sb.toString();
    }

    //SELECT ID USER
    public static String createQuerySELECTIDUSER(Class theClass){
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT ID FROM ").append(theClass.getSimpleName()).append(" ");
        sb.append("WHERE username = ?").append(" ").append("AND password = ?");

        return sb.toString();
    }

    //SELECT query passing an Object
    public static String createQuerySELECT(Object entity) {
        return createQuerySELECT2(entity.getClass());
    }

    //SELECT query passing a Class
    public static String createQuerySELECT2(Class theClass) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM ").append(theClass.getSimpleName());
        sb.append(" WHERE ID = ?");

        return sb.toString();
    }

    //DELETE query
    public static String createQueryDELETE(Object entity){
        StringBuffer sb = new StringBuffer();
        sb.append("DELETE FROM ").append(entity.getClass().getSimpleName()).append(" ");
        sb.append("WHERE ID = ?");

        return sb.toString();
    }

    //DELETE query with specified parameters
    public static String createQueryDELETE(Object object, HashMap params) {
        StringBuffer sb = new StringBuffer();
        String[] keys = new String[params.size()];
        int i = 0;

        if(object.getClass().getSuperclass().getSimpleName().equals("Object")) {
            sb.append("DELETE FROM ").append(object.getClass().getSimpleName()).append(" ");
        }
        else{
            sb.append("DELETE FROM ").append(object.getClass().getSuperclass().getSimpleName()).append(" ");
        }
        sb.append("WHERE ");

        for(Object key : params.keySet()){
            keys[i] = String.valueOf(key);
            i++;
        }

        for(int j = 0; j<keys.length; j++){
            Condition condition = (Condition) params.get(keys[j]);
            sb.append(keys[j]).append(condition.getS()).append(condition.getName()).append(" AND ");
        }

        sb.delete(sb.length() - 5,sb.length());

        return sb.toString();
    }

    //UPDATE query
    public static String createQueryUPDATE(Object entity){
        boolean noclass = false;
        StringBuffer sb = new StringBuffer();
        if(entity.getClass().getSuperclass().getSimpleName().equals("Object")) {
            sb.append("UPDATE ").append(entity.getClass().getSimpleName()).append(" ").append("SET");

            String [] fields = ObjectHelper.getFields(entity, noclass);

            for(String field: fields){
                sb.append(" ").append(field);
                sb.append(" = ?,");
            }
            sb.delete(sb.length() -1, sb.length());

            sb.append(" WHERE ID = ?");

            return sb.toString();
        }
        else{
            noclass = true;
            sb.append("UPDATE ").append(entity.getClass().getSuperclass().getSimpleName()).append(" ").append("SET");

            String [] fields = ObjectHelper.getFields(entity, noclass);

            for(String field: fields){
                sb.append(" ").append(field);
                sb.append(" = ?,");
            }
            sb.delete(sb.length() -1, sb.length());

            sb.append(" WHERE ID = ?");

            return sb.toString();
        }
    }

    //Find all query
    public static String findAllQuery(Class theClass) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM ").append(theClass.getSimpleName());

        return sb.toString();
    }

    //Query with conditions
    public static String findAllQuery(Class theClass, HashMap params) {
        Condition condition = (Condition) params.get("condition");

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM ").append(params.get("table")).append(" ");
        sb.append("WHERE ").append(params.get("column"));
        sb.append(condition.getS()).append("'").append(condition.getName()).append("'");

        return sb.toString();
    }

    //Query for 1:N relationship
    public static String findAllQuery(String query, MultiValueMap params) {
        StringBuffer sb = new StringBuffer();
        List<String> keys = new LinkedList<>();

        sb.append(query).append("WHERE ");

        Set entrySet = params.entrySet();
        Iterator it = entrySet.iterator();
        while (it.hasNext()) {
            Map.Entry mapEntry = (Map.Entry) it.next();
            keys.add(String.valueOf(mapEntry.getKey()));
        }

        for(Object key : keys){
            List<Condition> list = (List<Condition>) params.get(key);
            for(int i = 0; i<list.size(); i++) {
                sb.append(key).append(list.get(i).getS()).append(list.get(i).getName()).append(" AND ");
            }
        }

        sb.delete(sb.length() - 5,sb.length());

        return sb.toString();
    }
}
