package com.paxovision.trex.selenium.utils;

import com.paxovision.trex.selenium.exceptions.TrexSeleniumException;

import java.lang.reflect.Field;
import java.util.List;

public class RequiredList<T> {
    private final List<T> list;
    private final RequiredListBounds bounds;
    private final Class<?> genericType;

    @SuppressWarnings("unchecked")
    public RequiredList(Field field, Object in) {
        this.genericType = ReflectionUtil.getGenericTypeOfCollectionField(field);
        this.bounds = new RequiredListBounds(field);

        try {
            this.list = (List<T>) field.get(in);
        } catch (IllegalAccessException iae) {
            throw new TrexSeleniumException("Couldn't access value of object", iae);
        } catch (ClassCastException cce) {
            throw new IllegalArgumentException("Can not cast field of object to List");
        }
    }

    public List<T> list() {
        return list;
    }

    public Class<?> genericType() {
        return genericType;
    }

    public int atMost() {
        return bounds.atMost();
    }

    public int atLeast() {
        return bounds.atLeast();
    }
}
