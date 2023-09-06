package io.github.enoughsdv.refill.listeners;

import io.github.enoughsdv.refill.RefillPlugin;
import io.github.enoughsdv.refill.utils.MessageUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class RefillSignChangeListener implements Listener {

    private final FileConfiguration config;

    public RefillSignChangeListener(RefillPlugin refillPlugin) {
        this.config = refillPlugin.getConfig();
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        if (!event.getPlayer().hasPermission("refill.sign")) {
            return;
        }

        if (event.getLine(0).equals(config.getString("SIGN_SETTINGS.LINE_TO_SET"))) {
            int id = 0;

            for (String lines : config.getStringList("SIGN_SETTINGS.LINES")) {
                event.setLine(id, MessageUtil.translate(lines));
                id++;
            }
        }
    }
}
