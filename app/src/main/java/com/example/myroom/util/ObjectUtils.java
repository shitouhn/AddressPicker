package com.example.myroom.util;

/**
 * Created by zhong on 2017/7/20.
 */

@SuppressWarnings("all")
public class ObjectUtils {
    public static boolean isNull(Object obj){
        return obj == null;
    }

    public static boolean nonNull(Object obj) {
        return obj != null;
    }

    public static <T> T requireNonNull(T obj) {
        if (obj == null)
            throw new NullPointerException();
        return obj;
    }

    public static <T> T requireNonNull(T obj, String message) {
        if (obj == null)
            throw new NullPointerException(message);
        return obj;
    }

    public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }

    public static String toString(Object o) {
        return String.valueOf(o);
    }
}
