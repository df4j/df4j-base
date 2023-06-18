package com.df4j.base.exception;

public class BaseException extends RuntimeException {

    public BaseException(String msg) {
        super(msg);
    }

    public BaseException(String msg, Throwable t) {
        super(msg, t);
    }
}
