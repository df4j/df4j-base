package com.df4j.base.util;

import com.df4j.base.exception.BaseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

public class JsonUtils {
    private static ObjectMapper objectMapper = null;

    static {
        try {
            Class.forName("com.fasterxml.jackson.databind.ObjectMapper");
        } catch (Exception e) {
            throw BaseException.repack("load jackson ObjectMapper fail, please add jackson-databind to your project. ", e);
        }
        objectMapper = new ObjectMapper();
    }

    public static String stringfy(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw BaseException.repack(e);
        }
    }

    public static <T> T parse(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw BaseException.repack(e);
        }
    }

    public static <T> T parse(InputStream inputStream, Class<T> clazz) {
        try {
            return objectMapper.readValue(inputStream, clazz);
        } catch (Exception e) {
            throw BaseException.repack(e);
        }
    }
}