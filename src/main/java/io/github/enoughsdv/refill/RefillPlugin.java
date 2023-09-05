package io.github.enoughsdv.refill;

import io.github.enoughsdv.refill.listeners.RefillListener;

import org.bukkit.plugin.java.JavaPlugin;

public class RefillPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        this.getServer().getPluginManager()
                .registerEvents(new RefillListener(this), this);
    }
}
