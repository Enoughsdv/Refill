package io.github.enoughsdv.refill.listeners;

import io.github.enoughsdv.refill.RefillPlugin;
import io.github.enoughsdv.refill.utils.ItemBuilder;
import io.github.enoughsdv.refill.utils.MessageUtil;
import io.github.enoughsdv.refill.utils.CooldownUtil;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.inventory.InventoryClickEvent;

public class RefillListener implements Listener {

    private final FileConfiguration config;

    public RefillListener(RefillPlugin refillPlugin) {
        this.config = refillPlugin.getConfig();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || !(block.getState() instanceof Sign)) {
            return;
        }

        Sign sign = (Sign) block.getState();
        int id = 0;

        for (String lines : config.getStringList("SIGN_SETTINGS.LINES")) {
            if (sign.getLine(id).equalsIgnoreCase(MessageUtil.translate(lines))) {

                if (!CooldownUtil.isInCooldown(player.getUniqueId(), "REFILL")) {
                    player.openInventory(getInventory());
                    return;
                }

                player.sendMessage(MessageUtil.translate(config.getString("SIGN_SETTINGS.COOLDOWN_OPTIONS.IN_COOLDOWN_MESSAGE")
                        .replace("%time%", String.valueOf(CooldownUtil.getTimeLeft(player.getUniqueId(), "REFILL")))));
                return;
            }
            id++;
        }
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

    private ItemStack parseItem(String value) {
        Material material = Material.getMaterial(value);

        if (material != null) {
            return new ItemStack(material);
        }

        String type;
        String durabilityString;

        if (value.contains(":")) {
            String[] itemArray = value.split(":");
            if (itemArray.length < 2 || isInteger(itemArray[0]) || isInteger(itemArray[1])) {
                return null;
            }

            type = itemArray[0];
            durabilityString = itemArray[1];
        } else {
            type = value;
            durabilityString = "0";
        }

        material = Material.getMaterial(Integer.parseInt(type));

        if (material == null || isInteger(durabilityString)) {
            return null;
        }

        short durability = Short.parseShort(durabilityString);

        return new ItemStack(material, 1, durability);
    }

    private boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    private Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(null,
                config.getInt("INVENTORY_SETTINGS.OPTIONS.SIZE"),
                MessageUtil.translate(config.getString("INVENTORY_SETTINGS.OPTIONS.TITLE")));

        for (int i = 0; i < config.getInt("INVENTORY_SETTINGS.OPTIONS.SIZE"); i++) {
            inventory.setItem(i,
                    new ItemBuilder(Material.valueOf(config.getString("INVENTORY_SETTINGS.FILLER.MATERIAL")))
                            .amount(config.getInt("INVENTORY_SETTINGS.FILLER.AMOUNT"))
                            .durability((short) config.getInt("INVENTORY_SETTINGS.FILLER.DATA")).build());
        }

        for (String string : config.getStringList("INVENTORY_SETTINGS.ITEMS")) {
            if (string == null) {
                return inventory;
            }

            String[] data = string.split(", ");
            inventory.setItem(Integer.parseInt(data[2]),
                    new ItemBuilder(parseItem(data[0]))
                            .amount(Integer.parseInt(data[1])).build());
        }

        return inventory;
    }

}
