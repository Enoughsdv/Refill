package io.github.enoughsdv.refill.listeners;

import io.github.enoughsdv.refill.RefillPlugin;

import io.github.enoughsdv.refill.utils.MessageUtil;
import io.github.enoughsdv.refill.utils.CooldownUtil;
import io.github.enoughsdv.refill.utils.InventoryUtil;

import java.util.List;

import org.bukkit.block.Sign;
import org.bukkit.block.Block;

import org.bukkit.entity.Player;

import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

import org.bukkit.inventory.ItemStack;

import org.bukkit.configuration.file.FileConfiguration;

public class PlayerListener implements Listener {

    private final FileConfiguration config = RefillPlugin.getInstance().getConfig();
    private List<String> settingLines = config.getStringList("SIGN_SETTINGS.LINES");
    private String cooldownMessage = MessageUtil.translate(config.getString("SIGN_SETTINGS.COOLDOWN_OPTIONS.IN_COOLDOWN_MESSAGE"));
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK){
            return;
        }

        Block block = event.getClickedBlock();

        if(!(block.getState() instanceof Sign)){
            return;
        }

        Sign sign = (Sign) block.getState();
        int id = 0;

        for (String lines : settingLines) {

            if (sign.getLine(id).equalsIgnoreCase(MessageUtil.translate(lines))) {

                if (!CooldownUtil.isInCooldown(player.getUniqueId(), "REFILL")) {
                    player.openInventory(InventoryUtil.getInventory());
                    return;
                }

                player.sendMessage(
                    cooldownMessage.replace(
                        "%time%",
                        String.valueOf(CooldownUtil.getTimeLeft(player.getUniqueId(), "REFILL")))
                );
                return;
            }
            id++;
        }
    }
    

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getInventory().getTitle().equals(config.getString("INVENTORY_SETTINGS.OPTIONS.TITLE"))) {
            return;
        }

        ItemStack item = event.getCurrentItem();

        if (item == null) {
            return;
        }

        Player player = (Player) event.getWhoClicked();

        CooldownUtil cooldown = new CooldownUtil(player.getUniqueId(), "REFILL", config.getInt("SIGN_SETTINGS.COOLDOWN_OPTIONS.SECONDS"));
        cooldown.start();
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
