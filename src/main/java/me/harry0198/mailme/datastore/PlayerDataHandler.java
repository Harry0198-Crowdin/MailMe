package me.harry0198.mailme.datastore;

import me.harry0198.mailme.MailMe;
import org.bukkit.OfflinePlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class PlayerDataHandler {

    private final Map<UUID, PlayerData> playerData = new HashMap<>();
    private final MailMe plugin;

    public PlayerDataHandler(MailMe plugin) {
        this.plugin = plugin;
    }

    public PlayerData getPlayerData(OfflinePlayer player) {
        return getPlayerData(player.getUniqueId());
    }

    public PlayerData getPlayerData(UUID uuid) {
        if (!exists(uuid)) playerData.put(uuid, new PlayerData(plugin, uuid));
        return playerData.get(uuid);
    }

    private boolean exists(UUID uuid) {
        return playerData.containsKey(uuid);
    }
}
