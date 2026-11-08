package net.hypixel.commands;

import net.hypixel.Hypixel;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StuckCommand implements CommandExecutor {

    private final Hypixel plugin;

    public StuckCommand(Hypixel plugin) {
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

        plugin.getSpawnManager().teleportToSpawn(player);

        return true;
    }
}