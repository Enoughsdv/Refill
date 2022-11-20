package io.github.enoughsdv.refill.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemBuilder {

    private final ItemStack itemStack;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    /* Get the item */

    public ItemStack build() {
        return itemStack;
    }

    /*
     * Custom options for create the item
     */

    public ItemBuilder durability(int fillerData) {
        itemStack.setDurability((short)fillerData);
        return this;
    }

    public ItemBuilder amount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }
}
