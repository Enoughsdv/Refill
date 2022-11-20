package io.github.enoughsdv.refill.utils;

public class MessageUtil {
    
    private MessageUtil() {
        throw new java.lang.UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String translate(String message) {
        return message.replace('&', '§');
    }
}
