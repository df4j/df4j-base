package com.df4j.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CloseUtils {

    private static Logger logger = LoggerFactory.getLogger(CloseUtils.class);

    public static void close(AutoCloseable autoCloseable, Throwable t) {
        if (autoCloseable == null) {
            return;
        }
        try {
            autoCloseable.close();
        } catch (Exception ex) {
            if (t == null) {
                logger.debug("Exception occur when close a autoCloseable obj", ex);
            } else {
                t.addSuppressed(ex);
            }
        }
    }

    public static void close(AutoCloseable autoCloseable) {
        close(autoCloseable, null);
    }
}
