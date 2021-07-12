package net.enoughdv.refill.commands;

import net.enoughdv.refill.Refill;
import net.enoughdv.refill.utils.CC;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RefillCommand implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }
        
        Player player = (Player) sender;
        
        if(!player.hasPermission("refill.command")) {
            player.sendMessage(CC.translate(Refill.getInstance().getConfig().getString("NO_PERMISSIONS")));
            return true;
        }
        
        if(args.length == 0){
            player.sendMessage(CC.translate("&7&m---------------------")); 
            player.sendMessage(CC.translate("&b&lRefill"));
            player.sendMessage(CC.translate("&r")); 
            player.sendMessage(CC.translate("&eYou can look up how to create a poster on the spigot wiki.")); 
            player.sendMessage(CC.translate("&r")); 
            player.sendMessage(CC.translate("&7» &b/refill reload"));
            player.sendMessage(CC.translate("&7» &b/refill wiki"));
            player.sendMessage(CC.translate("&7&m---------------------")); 
        }
        
        if(args.length == 1){
            if(args[0].equalsIgnoreCase("reload")){
                Refill.getInstance().reloadConfig();
                player.sendMessage(CC.translate("&eReloading config..."));
                return true;
            }
            
            if(args[0].equalsIgnoreCase("wiki")){
                player.sendMessage(CC.translate("&e&lWIKI")); 
                return true;
            }
            
            player.sendMessage(CC.translate("&7&m---------------------")); 
            player.sendMessage(CC.translate("&b&lRefill"));
            player.sendMessage(CC.translate("&r")); 
            player.sendMessage(CC.translate("&eYou can look up how to create a poster on the spigot wiki.")); 
            player.sendMessage(CC.translate("&r")); 
            player.sendMessage(CC.translate("&7» &b/refill reload"));
            player.sendMessage(CC.translate("&7» &b/refill wiki"));
            player.sendMessage(CC.translate("&7&m---------------------")); 
            return true;
        }
        return true;
    }
    
}
