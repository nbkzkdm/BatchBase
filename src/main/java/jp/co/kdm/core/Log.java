package jp.co.kdm.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {
    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    public static void debug(Class<?> clazz, String message, Object... args) {
        Logger logger = getLogger(clazz);
        if (logger.isDebugEnabled()) {
            logger.debug("[DEBUG] " + message, args);
        }
    }

    public static void info(Class<?> clazz, String message, Object... args) {
        getLogger(clazz).info(message, args);
    }

    public static void warn(Class<?> clazz, String message, Object... args) {
        getLogger(clazz).warn(message, args);
    }

    public static void error(Class<?> clazz, String message, Object... args) {
        getLogger(clazz).error(message, args);
    }
}
