package com.df4j.base.task;

import java.util.HashMap;
import java.util.Map;

public class TaskCtx {
    private boolean reusable;

    private ClassLoader classLoader;

    private Throwable exception;

    private Map<String, Object> attributes;

    public void setReusable(boolean reusable) {
        this.reusable = reusable;
    }

    public boolean isReusable() {
        return reusable;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public void addAttribute(String key, Object attribute) {
        if (attributes == null) {
            attributes = new HashMap<>();
        }
        attributes.put(key, attribute);
    }

    public Object getAttribute(String key) {
        if (attributes != null) {
            return attributes.get(key);
        }
        return null;
    }

    public void removeAttribute(String key) {
        if (attributes == null) {
            return;
        }
        attributes.remove(key);
        if (attributes.isEmpty()) {
            attributes = null;
        }
    }

    public void exception(Throwable exception) {
        this.exception = exception;
    }

    public void resetException() {
        this.exception = null;
    }

    public Throwable getException() {
        return exception;
    }

    public void close() {

    }
}
