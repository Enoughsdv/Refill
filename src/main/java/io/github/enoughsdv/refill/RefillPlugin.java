package io.github.enoughsdv.refill;

import io.github.enoughsdv.refill.listeners.PlayerListener;
import io.github.enoughsdv.refill.utils.CooldownUtil;
import io.github.enoughsdv.refill.utils.InventoryUtil;

import org.bukkit.plugin.java.JavaPlugin;

public class RefillPlugin extends JavaPlugin {

    @Override
    public void onEnable() {

        InventoryUtil.startUtil(false);
        CooldownUtil.startUtil();
    
        this.saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public static RefillPlugin getInstance() {
        return JavaPlugin.getPlugin(RefillPlugin.class);
    }
}
