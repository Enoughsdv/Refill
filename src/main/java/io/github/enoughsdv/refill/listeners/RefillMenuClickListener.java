package io.github.enoughsdv.refill.listeners;

import io.github.enoughsdv.refill.RefillPlugin;
import io.github.enoughsdv.refill.utils.CooldownUtil;
import io.github.enoughsdv.refill.utils.MessageUtil;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class RefillMenuClickListener implements Listener {

    private final FileConfiguration config;

    public RefillMenuClickListener(RefillPlugin refillPlugin) {
        this.config = refillPlugin.getConfig();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (!event.getView().getTitle()
                .equals(MessageUtil.translate(config.getString("INVENTORY_SETTINGS.OPTIONS.TITLE")))) {
            return;
        }

        ItemStack item = event.getCurrentItem();

        if (item == null || item.getType().equals(Material.AIR)) {
            return;
        }

        CooldownUtil.startCooldown(player.getUniqueId(),
                "REFILL",
                config.getInt("SIGN_SETTINGS.COOLDOWN_OPTIONS.SECONDS"));
    }

}
