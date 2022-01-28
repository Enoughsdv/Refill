package net.enoughdv.refill.utils;

import java.util.List;
import java.util.ArrayList;

public class MessageUtil {

    public static String translate(String message) {
        return org.bukkit.ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> translate(List<String> messages) {
        List<String> buffered = new ArrayList<>();
        messages.forEach(message -> {
            buffered.add(translate("&r" + message));
        });
        return buffered;
    }
}
