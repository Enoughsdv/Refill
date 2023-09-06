package io.github.enoughsdv.refill.listeners;

import io.github.enoughsdv.refill.RefillPlugin;
import io.github.enoughsdv.refill.utils.MessageUtil;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class RefillBreakListener implements Listener {

    private final FileConfiguration config;

    public RefillBreakListener(RefillPlugin refillPlugin) {
        this.config = refillPlugin.getConfig();
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (!(block.getState() instanceof Sign)) {
            return;
        }

        Sign sign = (Sign) block.getState();
        int id = 0;

        for (String lines : config.getStringList("SIGN_SETTINGS.LINES")) {
            if (sign.getLine(id).equalsIgnoreCase(MessageUtil.translate(lines))) {
                if (!player.hasPermission("refill.sign")) {
                    player.sendMessage(MessageUtil.translate(config.getString("MESSAGES.BREAK_FAILED")));
                    event.setCancelled(true);
                    return;
                }

                if (!event.isCancelled()) {
                    player.sendMessage(MessageUtil.translate(config.getString("MESSAGES.BREAK_SUCCESSFULLY")));
                }
                return;
            }
            id++;
        }

    }

}
