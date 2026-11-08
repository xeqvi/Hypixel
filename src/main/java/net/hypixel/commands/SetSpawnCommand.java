package net.hypixel.commands;

import net.hypixel.Hypixel;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {

    private final Hypixel plugin;

    public SetSpawnCommand(Hypixel plugin) {
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
            sender.sendMessage(
                    ChatColor.RED + "Only players can use this command."
            );
            return true;
        }

        Player player = (Player) sender;

        Location location = player.getLocation();

        plugin.getSpawnManager().setSpawn(location);

        player.sendMessage(
                ChatColor.GREEN
                        + "Server spawn set to your current location."
        );

        return true;
    }
}
