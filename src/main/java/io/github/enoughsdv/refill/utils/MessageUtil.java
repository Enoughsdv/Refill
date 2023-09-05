package io.github.enoughsdv.refill.utils;

import java.util.List;
import java.util.stream.Collectors;

public class MessageUtil {

    private MessageUtil() {
        throw new java.lang.UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String translate(String message) {
        return org.bukkit.ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> translate(List<String> messages) {
        return messages.stream()
                .map(MessageUtil::translate)
                .collect(Collectors.toList());
    }
}
