package me.harry0198.mailme.datastore;

import me.harry0198.mailme.MailMe;
import me.harry0198.mailme.utility.Locale;
import me.harry0198.mailme.utility.Utils;
import org.bukkit.OfflinePlayer;

import java.io.File;
import java.io.IOException;
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
        if (exists(uuid)) return playerData.get(uuid);
        makeData(uuid);
        return playerData.get(uuid);
    }

    public void makeData(UUID uuid) {

        final File jsonData = new File(plugin.getDataFolder() + "/playerdata/" + uuid.toString() + ".json");
        try {
            jsonData.createNewFile();
        } catch (IOException io) {
            io.printStackTrace();
        }
        PlayerData data = Utils.readJson(jsonData);

        // If PlayerData exists in file
        if (data != null) {
            playerData.put(uuid, data);
            return;
        }

        PlayerData newData = new PlayerData(uuid, Locale.LANG.valueOf(plugin.getConfig().getString("lang")));
        Utils.writeJson(jsonData, newData);
        this.playerData.put(uuid, newData);
    }


    private boolean exists(UUID uuid) {
        return playerData.containsKey(uuid);
    }
}
