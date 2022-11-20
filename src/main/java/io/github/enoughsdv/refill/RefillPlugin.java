package io.github.enoughsdv.refill;

import io.github.enoughsdv.refill.listeners.PlayerListener;
import io.github.enoughsdv.refill.utils.InventoryUtil;

import org.bukkit.plugin.java.JavaPlugin;

public class RefillPlugin extends JavaPlugin {

    private static RefillPlugin main;

    @Override
    public void onEnable() {
        main = this;
        InventoryUtil.start();
        this.saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public static RefillPlugin getInstance() {
        return main;
    }
}
