package io.github.enoughsdv.refill.listeners;

import io.github.enoughsdv.refill.RefillPlugin;

import io.github.enoughsdv.refill.utils.MessageUtil;
import io.github.enoughsdv.refill.utils.CooldownUtil;
import io.github.enoughsdv.refill.utils.InventoryUtil;

import org.bukkit.block.Sign;

import java.util.List;

import org.bukkit.block.BlockState;

import org.bukkit.entity.Player;

import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import org.bukkit.configuration.file.FileConfiguration;

public class PlayerListener implements Listener {

    private final FileConfiguration config = RefillPlugin.getInstance().getConfig();
    private final String cooldownMessage = MessageUtil.translate(config.getString("SIGN_SETTINGS.COOLDOWN_OPTIONS.IN_COOLDOWN_MESSAGE"));
    private final List<String> signLines = MessageUtil.translateStrings(config.getStringList("SIGN_SETTINGS.LINES"));

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK){
            return;
        }

        BlockState blockState = event.getClickedBlock().getState();

        if(!(blockState instanceof Sign)){
            return;
        }

        Sign sign = (Sign)blockState;
        boolean equals = true;
        byte id = 0;

<<<<<<< HEAD
        for(String configLine : signLines){
            if(!sign.getLine(id).equals(configLine)){
                equals = false;
                break;
            }
            id++;
        }

        if(equals){
            if (!CooldownUtil.isInCooldown(player.getUniqueId())) {
                player.openInventory(InventoryUtil.getInventory());
=======
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
>>>>>>> 55f7f9651b9b48a90ebea5c03246af0a80a8ad9b
                return;
            }
    
            player.sendMessage(
                cooldownMessage.replace(
                    "%time%",
                    String.valueOf(CooldownUtil.getTimeLeft(player.getUniqueId())))
            );
        }
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        if (!event.getPlayer().hasPermission("refill.sign")) {
            return;
        }

        if (event.getLine(0).equalsIgnoreCase(config.getString("SIGN_SETTINGS.LINE_TO_SET"))) {
            byte id = 0;

            for (String lines : config.getStringList("SIGN_SETTINGS.LINES")) {
                event.setLine(id, MessageUtil.translate(lines));
                id++;
            }
        }
    }
}