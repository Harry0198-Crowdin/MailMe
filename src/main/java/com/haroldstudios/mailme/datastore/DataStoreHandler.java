/*
 *   Copyright [2020] [Harry0198]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.haroldstudios.mailme.datastore;

import com.haroldstudios.mailme.MailMe;
import com.haroldstudios.mailme.utility.Locale;
import com.haroldstudios.mailme.utility.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class DataStoreHandler {

    private final Map<UUID, PlayerData> playerData = new HashMap<>();
    private final MailMe plugin;
    /* Location of default mailbox location */
    public Location defaultLocation;

    public DataStoreHandler(MailMe plugin) {
        this.plugin = plugin;
        String world = plugin.getConfig().getString("default-mailbox.world");
        int x = plugin.getConfig().getInt("default-mailbox.x");
        int y = plugin.getConfig().getInt("default-mailbox.y");
        int z = plugin.getConfig().getInt("default-mailbox.z");
        defaultLocation = new Location(Bukkit.getWorld(world),x,y,z);
    }

    public PlayerData getPlayerData(OfflinePlayer player) {
        return getPlayerData(player.getUniqueId());
    }

    public PlayerData getPlayerData(UUID uuid) {
        if (!playerDataExists(uuid))
            makeData(uuid);
        return playerData.get(uuid);
    }

    public void forceSetPlayerData(PlayerData data) {
        if (playerData.containsKey(data.getUuid()))
            playerData.replace(data.getUuid(), data);
    }

    public PlayerData getTmpFromFile(File file) {
        return Utils.readJson(file);
    }

    public void removePlayerData(UUID uuid) {
        if (playerDataExists(uuid))
            playerData.remove(uuid);
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

    /**
     * Getter for the default mailbox location
     *
     * @return Location Object of mailbox
     */
    private Location getDefaultMailBoxLocation() {
        return defaultLocation;
    }

    public boolean playerDataExists(UUID uuid) {
        return playerData.containsKey(uuid);
    }

    public boolean playerDataFileExists(UUID uuid) {
        return new File(plugin.getDataFolder() + "/playerdata/" + uuid.toString() + ".json").exists();
    }

}
