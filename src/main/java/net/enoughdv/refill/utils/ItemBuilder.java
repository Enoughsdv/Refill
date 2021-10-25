package net.enoughdv.refill.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.enchantments.Enchantment;

import java.util.List;
import java.util.HashMap;

public class ItemBuilder {

    private Material material;
    private Short durability;
    private String title;
    private int amount = 1;
    private List<String> lores;
    private HashMap<Enchantment, Integer> enchantments = new HashMap<>();
    private ItemStack itemStack;
    private boolean unbreakable = false;

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
        
        ItemStack itemStack = this.itemStack;
        
        if (this.material != null) {
            itemStack.setType(this.material);
        }
        
        ItemMeta meta = itemStack.getItemMeta();
        
        meta.spigot().setUnbreakable(this.unbreakable);
        
        if (this.amount > 0)
            itemStack.setAmount(this.amount);
        if (this.durability != null)
            itemStack.setDurability(this.durability);
        if (this.title != null)
            meta.setDisplayName(MessageUtil.translate("&r" + this.title));
        if (this.lores != null && this.lores.size() > 0)
            meta.setLore(MessageUtil.translate(this.lores));
        itemStack.setItemMeta(meta);
        
        return itemStack;
    }
}