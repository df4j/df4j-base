package com.df4j.base.util;

public class StringUtils {
    public static boolean hasLength(String s) {
        return s != null && !s.isEmpty();
    }

    public static boolean hasText(String s) {
        return s != null && !s.isEmpty() && containsText(s);
    }

    private static boolean containsText(CharSequence str) {
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }
}
