package io.github.enoughsdv.refill.utils;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import io.github.enoughsdv.refill.RefillPlugin;

public class InventoryUtil {
    
    private static Inventory inventory = null;

    public static Inventory getInventory(){
        return inventory;
    }

    public static InventoryUtil start(){
        if(inventory == null){
            return new InventoryUtil();
        }
        return null;
    }

    private InventoryUtil(){
        FileConfiguration config = RefillPlugin.getInstance().getConfig();
        
        //InventoryHolder owner, int size, String title
        inventory = Bukkit.createInventory(
            null,
            config.getInt("INVENTORY_SETTINGS.OPTIONS.SIZE"),
            MessageUtil.translate(config.getString("INVENTORY_SETTINGS.OPTIONS.TITLE"))
        );

        int optionSize = config.getInt("INVENTORY_SETTINGS.OPTIONS.SIZE");
        int fillerData = config.getInt("INVENTORY_SETTINGS.FILLER.DATA");
        int fillerAmount = config.getInt("INVENTORY_SETTINGS.FILLER.AMOUNT");

        Material fillerMaterial = Material.valueOf(config.getString("INVENTORY_SETTINGS.FILLER.MATERIAL"));

        for (int i = 0; i < optionSize; i++) {
            inventory.setItem(i,
                new ItemBuilder(fillerMaterial)
                    .amount(fillerAmount)
                    .durability(fillerData).build());
        }

        List<String> items = config.getStringList("INVENTORY_SETTINGS.ITEMS");

        for (String string : items) {
            if (string == null) {
                return;
            }

            String[] data = string.split(", ");

            inventory.setItem(Integer.parseInt(data[2]),
                new ItemBuilder(parseItem(data[0]))
                    .amount(Integer.parseInt(data[1]))
                    .build());
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
