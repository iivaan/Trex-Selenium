package com.paxovision.trex.selenium.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ReflectionUtil {
    /**
     * Returns all fields of any scope, throughout the class hierarchy of the object, excluding
     * synthetic fields (like references to outer classes). All returned fields have accessibility
     * override flag set.
     */
    public static List<Field> getAllDeclaredFields(Object object) {
        List<Field> allFields = new ArrayList<>();
        Class<?> objClass = object.getClass();

        // Loop through the class hierarchy
        while (objClass != Object.class) {
            allFields.addAll(Arrays.asList(objClass.getDeclaredFields()));

            objClass = objClass.getSuperclass();
        }

        allFields.removeIf(Field::isSynthetic);
        allFields.forEach(f -> f.setAccessible(true));

        return allFields;
    }

    public static List<Class<?>> getAllInterfaces(Object object) {
        List<Class<?>> allInterfaces = new ArrayList<>();

        Class<?> objClass = object.getClass();

        // Loop through the class hierarchy
        while (objClass != Object.class) {
            allInterfaces.addAll(Arrays.asList(objClass.getInterfaces()));

            objClass = objClass.getSuperclass();
        }

        return allInterfaces;
    }

    // TODO: Add unit test
    public static Class<?> getGenericTypeOfCollectionField(Field field) {
        Type genericType = field.getGenericType();

        if (!(genericType instanceof ParameterizedType)) {
            throw new IllegalArgumentException("Field is not a ParameterizedType.");
        }

        Type[] genericTypes = ((ParameterizedType) genericType).getActualTypeArguments();


        if (genericTypes.length != 1) {
            throw new IllegalArgumentException("Field does not have a single generic type.");
        }

        if (!(genericTypes[0] instanceof Class)) {
            throw new IllegalArgumentException("Field's generic type is not a class?");
        }

        return (Class<?>) genericTypes[0];
    }
}
