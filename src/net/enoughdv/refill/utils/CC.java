package net.enoughdv.refill.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class CC {
    
    public static String translate(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    
    public static List<String> translate(List<String> messages) {
        List<String> buffered = new ArrayList<>();
        for (String message : messages){
            buffered.add(translate("&r" + message));
        }
        return buffered;
    }
}
