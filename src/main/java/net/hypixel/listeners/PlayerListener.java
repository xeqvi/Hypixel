package net.hypixel.listeners;

import net.hypixel.managers.PlayerDataManager;
import net.hypixel.managers.SpawnManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    private final PlayerDataManager playerDataManager;
    private final SpawnManager spawnManager;

    public PlayerListener(
            PlayerDataManager playerDataManager,
            SpawnManager spawnManager
    ) {
        this.playerDataManager = playerDataManager;
        this.spawnManager = spawnManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoinSpawn(PlayerJoinEvent event) {
        spawnManager.teleportToSpawn(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        playerDataManager.handleJoin(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        playerDataManager.handleQuit(event.getPlayer());
    }
}
