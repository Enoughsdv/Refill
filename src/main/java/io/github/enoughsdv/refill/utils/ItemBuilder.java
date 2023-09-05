package io.github.enoughsdv.refill.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {

    private Short durability;
    private int amount = 1;
    private final ItemStack itemStack;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemBuilder durability(short durability) {
        this.durability = durability;
        return this;
    }

    public ItemBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemStack build() {

        ItemStack itemStackB = this.itemStack;

        final ItemMeta meta = itemStackB.getItemMeta();

        if (this.amount > 0) {
            itemStackB.setAmount(this.amount);
        }

        if (this.durability != null) {
            itemStackB.setDurability(this.durability);
        }

        itemStackB.setItemMeta(meta);

        return itemStackB;
    }
}
