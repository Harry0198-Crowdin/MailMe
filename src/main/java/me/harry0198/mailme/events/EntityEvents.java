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

package me.harry0198.mailme.events;

import me.harry0198.mailme.MailMe;
import me.harry0198.mailme.datastore.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EntityEvents implements Listener {

    private final MailMe plugin;

    public EntityEvents(MailMe plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent e) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {

            PlayerData data = plugin.getPlayerDataHandler().getPlayerData(e.getPlayer().getUniqueId());

            if (data.getMail().stream().anyMatch(m -> !m.isRead())) {
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
        plugin.getPlayerDataHandler().removePlayerData(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null || !e.getClickedBlock().getType().equals(Material.CHEST)) return;

        Player player = e.getPlayer();
        PlayerData data = plugin.getPlayerDataHandler().getPlayerData(player);

        if (data.getMailBox() == null || !data.getMailBox().equals(e.getClickedBlock().getLocation()))
            return;

        e.setCancelled(true);
        plugin.getCmds().read(player);
    }

}
