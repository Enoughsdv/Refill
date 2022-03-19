package io.github.enoughsdv.refill;

import io.github.enoughsdv.refill.listeners.PlayerListener;

import org.bukkit.plugin.java.JavaPlugin;

public class RefillPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public static RefillPlugin getInstance() {
        return JavaPlugin.getPlugin(RefillPlugin.class);
    }
}
