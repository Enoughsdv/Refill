package io.github.enoughsdv.refill.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

public class MessageUtil {
    
    private MessageUtil() {
        throw new java.lang.UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> translateStrings(List<String> message) {
        List<String> buffered = new ArrayList<String>();
        for(String line : message){
            buffered.add(translate(line));
        }
        return buffered;
    }
}