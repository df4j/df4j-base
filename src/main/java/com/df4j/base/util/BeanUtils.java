package com.df4j.base.util;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class BeanUtils {
    public static void setProperty(Object target, String fieldName, Object value, boolean overrideNull) {
        if (target == null) {
            return;
        }
        if (value == null && !overrideNull) {
            return;
        }
        Field field = ReflectionUtils.findField(target.getClass(), fieldName);
        ReflectionUtils.makeAccessible(field);
        ReflectionUtils.setField(field, target, value);
    }

    public static void setProperty(Object target, String fieldName, Object value) {
        setProperty(target, fieldName, value, true);
    }

}
