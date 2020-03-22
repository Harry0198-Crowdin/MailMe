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
        if (!exists(uuid))
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
