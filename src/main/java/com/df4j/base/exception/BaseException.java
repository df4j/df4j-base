package com.df4j.base.exception;

public class BaseException extends RuntimeException {

    public BaseException(String msg) {
        super(msg);
    }

    public BaseException(Throwable t) {
        super(t);
    }

    public BaseException(String msg, Throwable t) {
        super(msg, t);
    }

    public static BaseException repack(Throwable e) {
        if (e instanceof BaseException) {
            return (BaseException) e;
        } else if (e instanceof RuntimeException) {
            return new BaseException("runtime exception", e);
        } else {
            return new BaseException("uncaught exception", e);
        }
    }

    public static BaseException repack(String msg, Throwable e) {
        if (e instanceof BaseException) {
            return (BaseException) e;
        } else if (e instanceof RuntimeException) {
            return new BaseException("runtime exception: " + msg, e);
        } else {
            return new BaseException("uncaught exception: " + msg, e);
        }
    }
}
