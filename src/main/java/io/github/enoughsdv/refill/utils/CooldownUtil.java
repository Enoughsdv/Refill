package io.github.enoughsdv.refill.utils;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.github.enoughsdv.refill.RefillPlugin;

public class CooldownUtil {
    public static final HashMap<UUID, Integer> cooldowns = new HashMap<>();
  
    private static int secondsRequeried;

    public static void startUtil(){
        secondsRequeried = RefillPlugin.getInstance().getConfig().getInt("null");
    }

    public static boolean isInCooldown(UUID id) {
        if (!cooldowns.containsKey(id)) {
            cooldowns.put(id, (int)System.currentTimeMillis());
            return false;
        }

        if(getTimeLeft(id) <= 0){
            cooldowns.remove(id);
            return true;
        }
        return true;
    }

    public static int getTimeLeft(UUID id) {
        int oldTime = CooldownUtil.cooldowns.get(id);
        int nowTime = (int)System.currentTimeMillis();

        int result =  (int) (TimeUnit.MILLISECONDS.toSeconds(nowTime) - TimeUnit.MILLISECONDS.toSeconds(oldTime));
        return secondsRequeried - result;
    }
}
