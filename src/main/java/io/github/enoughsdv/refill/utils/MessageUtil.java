package io.github.enoughsdv.refill.utils;

import java.util.List;
import java.util.ArrayList;

public class MessageUtil {
    
    private MessageUtil() {
        throw new java.lang.UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
    
    public static String translate(String message) {
        return org.bukkit.ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> translate(List<String> messages) {
        List<String> buffered = new ArrayList<>();
        messages.forEach(message -> buffered.add(translate(message)));
        return buffered;
    }
}
