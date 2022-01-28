package net.enoughdv.refill;

import net.enoughdv.refill.command.RefillCommand;
import net.enoughdv.refill.listeners.PlayerListener;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class RefillPlugin extends JavaPlugin {

    private static RefillPlugin instance;

    @Override
    public void onEnable() {
        instance = this;

        this.saveDefaultConfig();

        this.getCommand("refill").setExecutor(new RefillCommand());
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public static RefillPlugin getInstance() {
        return instance;
    }
}
