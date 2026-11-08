package net.hypixel.managers;

import net.hypixel.Hypixel;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class ServerManager implements PluginMessageListener {

    private final Hypixel plugin;
    private final Random random = new Random();

    private final Map<UUID, Boolean> pendingRequests = new HashMap<>();

    public ServerManager(Hypixel plugin) {
        this.plugin = plugin;
    }

    public String getServerName() {
        return plugin.getConfig().getString("server-name");
    }

    public List<String> getLobbyServers() {
        return plugin.getConfig().getStringList("lobby-servers");
    }

    public boolean isLobbyServer() {
        return getLobbyServers().contains(getServerName());
    }

    public void sendToRandomLobby(Player player) {

        pendingRequests.put(player.getUniqueId(), true);

        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(bytes);

            out.writeUTF("GetServers");

            player.sendPluginMessage(
                    plugin,
                    "BungeeCord",
                    bytes.toByteArray()
            );

        } catch (IOException exception) {
            pendingRequests.remove(player.getUniqueId());
            exception.printStackTrace();
        }
    }

    @Override
    public void onPluginMessageReceived(
            String channel,
            Player player,
            byte[] message
    ) {

        if (!channel.equals("BungeeCord")) {
            return;
        }

        try {
            DataInputStream in = new DataInputStream(
                    new ByteArrayInputStream(message)
            );

            String subChannel = in.readUTF();

            if (!subChannel.equals("GetServers")) {
                return;
            }

            String serverList = in.readUTF();

            UUID uuid = player.getUniqueId();

            if (!pendingRequests.containsKey(uuid)) {
                return;
            }

            pendingRequests.remove(uuid);

            List<String> availableLobbies = new ArrayList<>();

            for (String lobby : getLobbyServers()) {
                if (serverList.contains(lobby)) {
                    availableLobbies.add(lobby);
                }
            }

            if (availableLobbies.isEmpty()) {
                player.sendMessage(
                        ChatColor.RED + "No available lobby servers."
                );
                return;
            }

            String selectedLobby = availableLobbies.get(
                    random.nextInt(availableLobbies.size())
            );

            // Send one empty chat line.
            player.sendMessage("");

            connect(player, selectedLobby);

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void connect(Player player, String server) {

        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(bytes);

            out.writeUTF("Connect");
            out.writeUTF(server);

            player.sendPluginMessage(
                    plugin,
                    "BungeeCord",
                    bytes.toByteArray()
            );

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}