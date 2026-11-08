package net.hypixel;

import net.hypixel.commands.LobbyCommand;
import net.hypixel.commands.SpawnCommand;
import net.hypixel.commands.StuckCommand;
import net.hypixel.database.DatabaseManager;
import net.hypixel.listeners.PlayerListener;
import  net.hypixel.managers.PlayerDataManager;
import net.hypixel.managers.ServerManager;
import net.hypixel.managers.SpawnManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Hypixel extends JavaPlugin {

    private DatabaseManager databaseManager;
    private PlayerDataManager playerDataManager;
    private ServerManager serverManager;
    private SpawnManager spawnManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.serverManager = new ServerManager(this);
        this.spawnManager = new SpawnManager(this);

        try {
            databaseManager = new DatabaseManager(this);
            databaseManager.connect();
            databaseManager.createTables();

            playerDataManager = new PlayerDataManager(this, databaseManager);
            getServer().getPluginManager().registerEvents(
                    new PlayerListener(playerDataManager), this
            );
            getServer().getMessenger().registerOutgoingPluginChannel(
                    this, "BungeeCord"
            );
            getServer().getMessenger().registerIncomingPluginChannel(
                    this, "BungeeCord", serverManager
            );

            getCommand("spawn").setExecutor(new SpawnCommand(this));
            getCommand("lobby").setExecutor(new LobbyCommand(this));
            getCommand("stuck").setExecutor(new StuckCommand(this));

            playerDataManager.startAutoSave();

            getLogger().info("Hypixel Has been enabled!");
        } catch (Exception exception) {
            getLogger().severe("Could not initialize Hypixel!");
            exception.printStackTrace();

            if (playerDataManager != null) {
                playerDataManager.shutdown();
            }

            if (databaseManager != null) {
                databaseManager.close();
            }

            getServer().getPluginManager().disablePlugin(this);

        }
    }

    @Override
    public void onDisable() {
        if (playerDataManager != null) {
            playerDataManager.shutdown();
        }

        if (databaseManager != null) {
            databaseManager.close();
        }

        getLogger().info("Hypixel has been disabled!");

    }

    public ServerManager getServerManager() {
        return serverManager;
    }

    public SpawnManager getSpawnManager() {
        return spawnManager;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }
}
