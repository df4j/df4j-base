package com.df4j.base.util;

import com.df4j.base.exception.BaseException;

public class ObjectUtils {
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isNotNull(Object obj) {
        return obj != null;
    }

    public static void requireNotNull(Object obj, String msg) {
        if (obj == null) {
            throw new BaseException(msg);
        }
    }

    public static void requireNotNull(Object obj) {
        if (obj == null) {
            throw new BaseException("obj require not null");
        }
    }
}
