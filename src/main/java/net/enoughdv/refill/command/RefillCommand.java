package net.enoughdv.refill.command;

import net.enoughdv.refill.RefillPlugin;
import net.enoughdv.refill.utils.MessageUtil;

import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class RefillCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("refill.command")) {
            player.sendMessage(MessageUtil.translate(RefillPlugin.getInstance().getConfig().getString("NO_PERMISSIONS")));
            return true;
        }

        if (args.length == 0) {
            sendUsage(player);
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            RefillPlugin.getInstance().reloadConfig();
            player.sendMessage(MessageUtil.translate("&eReloading config..."));
            return true;
        }

        sendUsage(player);
        return true;
    }

    private void sendUsage(Player player) {
        player.sendMessage(MessageUtil.translate("&7&m---------------------"));
        player.sendMessage(MessageUtil.translate("&b&lRefill"));
        player.sendMessage(MessageUtil.translate("&r"));
        player.sendMessage(MessageUtil.translate("&7» &b/refill reload"));
        player.sendMessage(MessageUtil.translate("&7&m---------------------"));
    }

}
