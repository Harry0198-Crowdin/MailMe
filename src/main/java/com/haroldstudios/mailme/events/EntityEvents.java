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

package com.haroldstudios.mailme.events;

import com.google.gson.reflect.TypeToken;
import com.haroldstudios.mailme.MailMe;
import com.haroldstudios.mailme.datastore.PlayerData;
import com.haroldstudios.mailme.mail.Mail;
import com.haroldstudios.mailme.utility.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.lang.reflect.Type;
import java.util.Collections;

public class EntityEvents implements Listener {

    private final MailMe plugin;

    public EntityEvents(MailMe plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent e) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {

            if (!plugin.getDataStoreHandler().playerDataFileExists(e.getPlayer().getUniqueId())) {

                String welcomeKey = "welcome";

                // If welcome mail exists
                if (Utils.isValidPresetKey(plugin, welcomeKey)) {
                    ConfigurationSection presets = plugin.getConfig().getConfigurationSection("presets");
                    String preset = presets.getString(welcomeKey);
                    Type token = new TypeToken<Mail>() {}.getType();
                    Mail mail = MailMe.GSON.fromJson(preset, token);
                    mail.clearRecipients();
                    mail.addRecipients(Collections.singletonList(e.getPlayer()));

                    Bukkit.getScheduler().runTask(plugin, mail::sendAsAdmin); //TODO works?
                }
            }

            PlayerData data = plugin.getDataStoreHandler().getPlayerData(e.getPlayer().getUniqueId());

            if (data.hasUnreadMail()) {
                e.getPlayer().sendMessage(plugin.getLocale().getMessage(data.getLang(), "cmd.mail-unread"));
            }

            if (data.getMailBox() != null) {
                MailMe.playerList.add(e.getPlayer());
            }

        });
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {

        if (MailMe.playerList.contains(e.getPlayer())) {
            MailMe.playerList.add(e.getPlayer());
        }
        plugin.getDataStoreHandler().removePlayerData(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null || !e.getClickedBlock().getType().equals(Material.CHEST)) return;

        Player player = e.getPlayer();
        PlayerData data = plugin.getDataStoreHandler().getPlayerData(player);

        if (data.getMailBox() == null || !data.getMailBox().equals(e.getClickedBlock().getLocation()))
            return;

        MailBoxOpenedEvent mailBoxOpenedEvent = new MailBoxOpenedEvent(data);
        Bukkit.getPluginManager().callEvent(mailBoxOpenedEvent);

        if (mailBoxOpenedEvent.isCancelled())
            return;

        e.setCancelled(true);
        plugin.getCmds().read(player);
    }

}
