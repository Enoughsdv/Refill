package net.enoughdv.refill.listeners;

import net.enoughdv.refill.RefillPlugin;
import net.enoughdv.refill.utils.ItemBuilder;
import net.enoughdv.refill.utils.MessageUtil;
import net.enoughdv.refill.utils.CooldownUtil;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PlayerListener implements Listener {

    private final FileConfiguration config = RefillPlugin.getInstance().getConfig();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        int id = 0;

        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        Inventory inventory = Bukkit.createInventory(null,
                config.getInt("INVENTORY_SETTINGS.OPTIONS.SIZE"),
                MessageUtil.translate(config.getString("INVENTORY_SETTINGS.OPTIONS.TITLE")));

        for (int i = 0; i < config.getInt("INVENTORY_SETTINGS.OPTIONS.SIZE"); i++) {
            inventory.setItem(i, new ItemBuilder()
                    .material(Material.valueOf(config.getString("INVENTORY_SETTINGS.FILLER.MATERIAL")))
                    .amount(config.getInt("INVENTORY_SETTINGS.FILLER.AMOUNT"))
                    .durability((short) config.getInt("INVENTORY_SETTINGS.FILLER.DATA"))
                    .build());
        }

        for (String string : config.getStringList("INVENTORY_SETTINGS.ITEMS")) {
            if (string == null) {
                return;
            }

            String[] data = string.split(", ");
            inventory.setItem(Integer.parseInt(data[2]),
                    new ItemBuilder(parseItem(data[0]))
                            .amount(Integer.parseInt(data[1])).build());
        }

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && block.getState() instanceof Sign) {

            Sign sign = (Sign) block.getState();

            for (String lines : config.getStringList("SIGN_SETTINGS.LINES")) {
                if (sign.getLine(id).equalsIgnoreCase(MessageUtil.translate(lines))) {

                    if (!CooldownUtil.isInCooldown(player.getUniqueId(), "REFILL")) {

                        player.openInventory(inventory);

                        return;
                    }

                    player.sendMessage(MessageUtil.translate(config.getString("SIGN_SETTINGS.COOLDOWN_OPTIONS.IN_COOLDOWN_MESSAGE")
                            .replace("%time%", String.valueOf(CooldownUtil.getTimeLeft(player.getUniqueId(), "REFILL")))));
                    return;
                }
                id++;
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getInventory().getTitle() == null ? config.getString("INVENTORY_SETTINGS.OPTIONS.TITLE") != null : !event.getInventory().getTitle().equals(config.getString("INVENTORY_SETTINGS.OPTIONS.TITLE"))) {
            return;
        }

        ItemStack item = event.getCurrentItem();

        if (item == null || item.getType() == Material.AIR) {
            return;
        }

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

    private ItemStack parseItem(String value) {

        Material material = Material.getMaterial(value);

        if (material != null) {
            return new ItemStack(material);
        }

        String type;
        String durabilityString;

        if (value.contains(":")) {
            String[] itemArray = value.split(":");
            if (itemArray.length < 2) {
                return null;
            }

            type = itemArray[0];
            durabilityString = itemArray[1];
        } else {
            type = value;
            durabilityString = "0";
        }

        if (!isInteger(type)) {
            return null;
        }

        material = Material.getMaterial(Integer.parseInt(type));

        if (material == null || !isInteger(durabilityString)) {
            return null;
        }

        short durability = Short.parseShort(durabilityString);

        return new ItemStack(material, 1, durability);
    }

    private boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
