package net.hypixel.managers;

import net.hypixel.Hypixel;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class SpawnManager {

    private final Hypixel plugin;

    private Location spawn;

    public SpawnManager(Hypixel plugin) {
        this.plugin = plugin;
        loadSpawn();
    }

    private void loadSpawn() {

        ConfigurationSection section =
                plugin.getConfig().getConfigurationSection("spawn");

        if (section == null) {
            return;
        }

        String worldName = section.getString("world");

        if (worldName == null || worldName.isEmpty()) {
            return;
        }

        World world = Bukkit.getWorld(worldName);

        if (world == null) {
            plugin.getLogger().warning(
                    "Spawn world '" + worldName + "' is not loaded!"
            );
            return;
        }

        if (!section.isSet("x")
                || !section.isSet("y")
                || !section.isSet("z")) {

            spawn = world.getSpawnLocation();
            return;
        }

        spawn = new Location(
                world,
                section.getDouble("x"),
                section.getDouble("y"),
                section.getDouble("z"),
                (float) section.getDouble("yaw"),
                (float) section.getDouble("pitch")
        );
    }

    public void setSpawn(Location location) {

        spawn = location.clone();
        saveSpawn();
    }

    private void saveSpawn() {

        World world = spawn.getWorld();

        plugin.getConfig().set(
                "spawn.world",
                world == null ? "" : world.getName()
        );
        plugin.getConfig().set("spawn.x", spawn.getX());
        plugin.getConfig().set("spawn.y", spawn.getY());
        plugin.getConfig().set("spawn.z", spawn.getZ());
        plugin.getConfig().set("spawn.yaw", (double) spawn.getYaw());
        plugin.getConfig().set("spawn.pitch", (double) spawn.getPitch());

        plugin.saveConfig();
    }

    public Location getSpawn() {
        return spawn == null ? null : spawn.clone();
    }

    public boolean hasSpawn() {
        return spawn != null;
    }

    public void teleportToSpawn(Player player) {

        if (spawn == null) {
            loadSpawn();
        }

        if (spawn != null) {
            player.teleport(spawn);
            return;
        }

        player.teleport(player.getWorld().getSpawnLocation());
    }
}
