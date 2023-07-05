package com.df4j.base.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

    private static Map<Integer, Map<String, Pattern>> patternMapMap = new ConcurrentHashMap<>();

    public static Pattern pattern(String pattern, int flag) {
        if (!patternMapMap.containsKey(flag)) {
            synchronized (patternMapMap) {
                if (!patternMapMap.containsKey(flag)) {
                    patternMapMap.put(flag, new ConcurrentHashMap<>());
                }
            }
        }
        Map<String, Pattern> patternMap = patternMapMap.get(flag);

        if (!patternMap.containsKey(pattern)) {
            synchronized (patternMap) {
                if (!patternMap.containsKey(pattern)) {
                    patternMap.put(pattern, Pattern.compile(pattern));
                }
            }
        }
        return patternMap.get(pattern);
    }

    public static Pattern pattern(String pattern) {
        return pattern(pattern, 0);
    }

    public static void clearPatternCache(String pattern, int flag) {
        if (!patternMapMap.containsKey(flag)) {
            return;
        }
        Map<String, Pattern> patternMap = patternMapMap.get(flag);
        if (!patternMap.containsKey(pattern)) {
            return;
        }
        synchronized (patternMap) {
            if (patternMap.containsKey(pattern)) {
                patternMap.remove(pattern);
            }
        }
    }

    public static void clearPatternCache(String pattern) {
        clearPatternCache(pattern, 0);
    }

    public static boolean test(String regex, String content) {
        return pattern(regex).matcher(content).matches();
    }

    public static boolean contains(String regex, String content) {
        return pattern(regex).matcher(content).find();
    }

    public static String getFirstStr(String regex, String content) {
        Matcher matcher = pattern(regex).matcher(content);
        return matcher.find() ? matcher.group() : null;
    }

    public static List<String> getAllStr(String regex, String content) {
        Matcher matcher = pattern(regex).matcher(content);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return list;
    }
}
