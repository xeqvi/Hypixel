package net.hypixel.commands;

import net.hypixel.Hypixel;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    private final Hypixel plugin;

    public SpawnCommand(Hypixel plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (plugin.getServerManager().isLobbyServer()) {
            player.sendMessage(
                    ChatColor.RED + "You are already connected to this server"
            );
            return true;
        }

        plugin.getServerManager().sendToRandomLobby(player);

        return true;
    }
}