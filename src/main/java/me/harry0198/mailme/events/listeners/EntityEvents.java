package me.harry0198.mailme.events.listeners;

import me.harry0198.mailme.MailMe;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public final class EntityEvents implements Listener {

    private final MailMe plugin;

    public EntityEvents(MailMe plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        plugin.getPlayerDataHandler().getPlayerData(e.getPlayer());
    }
}
