package net.enoughdv.refill;

import net.enoughdv.refill.commands.RefillCommand;
import net.enoughdv.refill.listeners.PlayerListener;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Refill extends JavaPlugin {
    
    @Getter private static Refill instance;
    
    @Override
    public void onEnable(){
        instance = this;
        this.saveDefaultConfig();
        
        this.getCommand("refill").setExecutor(new RefillCommand());
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }
}
