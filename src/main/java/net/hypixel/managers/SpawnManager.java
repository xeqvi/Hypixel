package net.hypixel.managers;

import net.hypixel.Hypixel;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SpawnManager {

    private final Hypixel plugin;

    public SpawnManager(Hypixel plugin) {
        this.plugin = plugin;
    }

    public void teleportToSpawn(Player player) {
        Location spawn = player.getWorld().getSpawnLocation();
        player.teleport(spawn);
    }
}
