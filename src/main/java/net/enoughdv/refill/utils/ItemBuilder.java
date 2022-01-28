package net.enoughdv.refill.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemBuilder {

    private Material material;
    private Short durability;
    private String title;
    private int amount = 1;
    private List<String> lores;
    private final ItemStack itemStack;
    private final boolean unbreakable = false;

    public ItemBuilder() {
        this.itemStack = new ItemStack(Material.AIR);
    }

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemBuilder material(Material material) {
        this.material = material;
        return this;
    }

    public ItemBuilder durability(short durability) {
        this.durability = durability;
        return this;
    }

    public ItemBuilder title(String title) {
        this.title = title;
        return this;
    }

    public ItemBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemStack build() {

        ItemStack itemStackB = this.itemStack;

        if (this.material != null) {
            itemStackB.setType(this.material);
        }

        ItemMeta meta = itemStackB.getItemMeta();

        meta.spigot().setUnbreakable(this.unbreakable);

        if (this.amount > 0) {
            itemStackB.setAmount(this.amount);
        }
        if (this.durability != null) {
            itemStackB.setDurability(this.durability);
        }
        if (this.title != null) {
            meta.setDisplayName(MessageUtil.translate("&r" + this.title));
        }
        if (this.lores != null && !this.lores.isEmpty()) {
            meta.setLore(MessageUtil.translate(this.lores));
        }
        itemStackB.setItemMeta(meta);

        return itemStackB;
    }
}
