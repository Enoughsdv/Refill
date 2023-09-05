package io.github.enoughsdv.refill.utils;

import java.util.Map;
import java.util.UUID;
import java.util.HashMap;

public class CooldownUtil {

    private static final Map<String, CooldownUtil> cooldowns = new HashMap<>();
    private final long start;
    private final int timeInSeconds;

    private CooldownUtil(int timeInSeconds) {
        this.timeInSeconds = timeInSeconds;
        this.start = System.currentTimeMillis();
    }

    public static boolean isInCooldown(UUID id, String cooldownName) {
        if (getTimeLeft(id, cooldownName) >= 1) {
            return true;
        } else {
            stop(id, cooldownName);
            return false;
        }
    }

    private static void stop(UUID id, String cooldownName) {
        cooldowns.remove(getCooldownKey(id, cooldownName));
    }

    private static CooldownUtil getCooldown(UUID id, String cooldownName) {
        return cooldowns.get(getCooldownKey(id, cooldownName));
    }

    private static String getCooldownKey(UUID id, String cooldownName) {
        return id.toString() + cooldownName;
    }

    public static int getTimeLeft(UUID id, String cooldownName) {
        CooldownUtil cooldown = getCooldown(id, cooldownName);
        if (cooldown != null) {
            long now = System.currentTimeMillis();
            int elapsedTimeInSeconds = (int) (now - cooldown.start) / 1000;
            return Math.max(cooldown.timeInSeconds - elapsedTimeInSeconds, 0);
        }
        return -1;
    }

    public static void startCooldown(UUID id, String cooldownName, int timeInSeconds) {
        CooldownUtil cooldown = new CooldownUtil(timeInSeconds);
        cooldowns.put(getCooldownKey(id, cooldownName), cooldown);
    }
}