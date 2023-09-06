package io.github.enoughsdv.refill;

import io.github.enoughsdv.refill.listeners.RefillBreakListener;
import io.github.enoughsdv.refill.listeners.RefillMenuClickListener;
import io.github.enoughsdv.refill.listeners.RefillInteractListener;
import io.github.enoughsdv.refill.listeners.RefillSignChangeListener;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class RefillPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        Arrays.asList(
                new RefillBreakListener(this),
                new RefillMenuClickListener(this),
                new RefillInteractListener(this),
                new RefillSignChangeListener(this))
            .forEach(listener -> this.getServer().getPluginManager().registerEvents(listener, this));
    }

}
