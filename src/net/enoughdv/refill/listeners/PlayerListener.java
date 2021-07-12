package net.enoughdv.refill.listeners;

import net.enoughdv.refill.Refill;
import net.enoughdv.refill.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class PlayerListener implements Listener {
    
    FileConfiguration config = Refill.getInstance().getConfig();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        int id = 0;
        Player player = event.getPlayer(); Block block = event.getClickedBlock(); 
        
        Inventory inventory = Bukkit.createInventory(null, config.getInt("INVENTORY_SETTINGS.OPTIONS.SIZE"), CC.translate(config.getString("INVENTORY_SETTINGS.OPTIONS.TITLE")));
        
        for(int i = 0; i < config.getInt("INVENTORY_SETTINGS.OPTIONS.SIZE"); i++){
            inventory.setItem(i, new ItemBuilder()
            .material(Material.valueOf(config.getString("INVENTORY_SETTINGS.FILLER.MATERIAL")))
            .amount(config.getInt("INVENTORY_SETTINGS.FILLER.AMOUNT"))
            .durability((short)config.getInt("INVENTORY_SETTINGS.FILLER.DATA"))
            .build());
        }
        
        for(String string : config.getStringList("INVENTORY_SETTINGS.ITEMS")){
            if (string == null) return;
            String[] data = string.split(", ");
            inventory.setItem(Integer.parseInt(data[2]), new ItemBuilder()
            .material(Material.valueOf(data[0].toUpperCase()))
            .amount(Integer.parseInt(data[1]))
            .build());
        }
        
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK && block.getState() instanceof Sign){
            
            Sign sign = (Sign) block.getState();

            for(String lines : config.getStringList("SIGN_SETTINGS.LINES")){
                if(sign.getLine(id).equalsIgnoreCase(CC.translate(lines))){
                    if(!CooldownUtil.isInCooldown(player.getUniqueId(), "REFILL")){
                        player.openInventory(inventory);
                        CooldownUtil c = new CooldownUtil(player.getUniqueId(), "REFILL", config.getInt("SIGN_SETTINGS.COOLDOWN_OPTIONS.SECONDS"));
                        c.start();
                        return;
                    }
                    
                    player.sendMessage(CC.translate(config.getString("SIGN_SETTINGS.COOLDOWN_OPTIONS.IN_COOLDOWN_MESSAGE")
                    .replace("%time%", String.valueOf(CooldownUtil.getTimeLeft(player.getUniqueId(), "REFILL")))));            
                    return;
                }
                id++;
            }        
        }  
    }
    
    @EventHandler
    public void onSignChange(SignChangeEvent event){
        if(event.getPlayer().hasPermission("refill.sign")){
            if(event.getLine(0).equals("[Arena-Refill]")){
                int id = 0;
                for(String lines : config.getStringList("SIGN_SETTINGS.LINES")){
                    event.setLine(id, CC.translate(lines));
                    id++;
                }
            }
        }
    }
    
}
