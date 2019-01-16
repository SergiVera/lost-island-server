package edu.upc.eetac.dsa.util;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ObjectHelper {
    public static String[] getFields(Object entity, boolean noclass) {

        Class theClass = entity.getClass();

        if(noclass == false){
            Field[] fields = theClass.getDeclaredFields();

            String[] sFields = new String[fields.length];
            int i=0;

            for (Field f: fields) sFields[i++]=f.getName();

            return sFields;
        }
        else{
            Field[] fields = theClass.getSuperclass().getDeclaredFields();

            String[] sFields = new String[fields.length];
            int i=0;

            for (Field f: fields) sFields[i++]=f.getName();

            return sFields;
        }

    }


    public static void setter(Object entity, String property, Object value) throws InvocationTargetException, IllegalAccessException {
        // Method // invoke
        Object result = null;
        Class theClass = entity.getClass();
        Method[] methods = theClass.getMethods();
        //Method setter = theClass.getMethod(property, String.class);

        for(Method method : methods){
            if(isSetter(method)){
                if(method.getName().regionMatches(true, 3, property, 0, property.length()))
                    method.invoke(entity,value);
            }

        }

    }

    private String getMethodName(String property) {
        //property: "name"

        // "get"+"N"+"ame"
        return "get"+property.substring(0,1).toUpperCase()+property.substring(1);
    }

    public static Object getter(Object entity, String property) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        // Method // invoke
        Object result = null;
        Class theClass = entity.getClass();
        Method[] methods = theClass.getMethods();

        //Method getter = theClass.getMethod(property, null);




        for(Method method : methods){
            if(isGetter(method)){
                if((method.getName().regionMatches(true, 3, property, 0, property.length())) || (method.getName().regionMatches(true, 2, property, 0, property.length())))
                    result =method.invoke(entity);
            }
        }

        return result;
    }

    public static boolean isGetter(Method method){
        if(!method.getName().startsWith("get"))      return false;
        if(method.getParameterTypes().length != 0)   return false;
        if(void.class.equals(method.getReturnType())) return false;
        return true;
    }

    public static boolean isSetter(Method method){
        if(!method.getName().startsWith("set")) return false;
        if(method.getParameterTypes().length != 1) return false;
        return true;
    }
}
