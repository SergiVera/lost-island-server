package edu.upc.eetac.dsa.util;

public class QueryHelper {

    public static String createQueryINSERT(Object entity) {

        StringBuffer sb = new StringBuffer("INSERT INTO ");
        sb.append(entity.getClass().getSimpleName()).append(" ");
        sb.append("(");

        String [] fields = ObjectHelper.getFields(entity);

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

    public static String createQuerySELECTIDUSER(Class theClass){
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT ID FROM ").append(theClass.getSimpleName()).append(" ");
        sb.append("WHERE username = ?").append(" ").append("AND password = ?");

        return sb.toString();
    }

    public static String createQuerySELECT(Object entity) {
        return createQuerySELECT2(entity.getClass());
    }

    public static String createQuerySELECT2(Class theClass) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM ").append(theClass.getSimpleName());
        sb.append(" WHERE ID = ?");

        return sb.toString();
    }

    public static String createQueryDELETE(Object entity){
        StringBuffer sb = new StringBuffer();
        sb.append("DELETE FROM ").append(entity.getClass().getSimpleName()).append(" ");
        sb.append("WHERE ID = ?");

        return sb.toString();
    }

    public static String createQueryUPDATE(Object entity){
        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE ").append(entity.getClass().getSimpleName()).append(" ").append("SET");

        String [] fields = ObjectHelper.getFields(entity);

        for(String field: fields){
            sb.append(" ").append(field);
            sb.append(" = ?,");
        }
        sb.delete(sb.length() -1, sb.length());

        sb.append(" WHERE ID = ?");

        return sb.toString();
    }

    public static String findAllQuery(Class theClass) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM ").append(theClass.getSimpleName());

        return sb.toString();
    }
}
