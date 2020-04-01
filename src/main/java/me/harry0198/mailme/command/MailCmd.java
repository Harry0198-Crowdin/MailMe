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

package me.harry0198.mailme.command;

import me.harry0198.mailme.MailMe;
import me.harry0198.mailme.datastore.PlayerData;
import me.harry0198.mailme.mail.Mail;
import me.harry0198.mailme.ui.MailGui;
import me.harry0198.mailme.utility.Locale;
import me.harry0198.mailme.utility.Pagination;
import me.mattstudios.mf.annotations.*;
import me.mattstudios.mf.annotations.Optional;
import me.mattstudios.mf.base.CommandBase;
import me.mattstudios.mf.exceptions.SyntaxError;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Command("mail")
@Alias("mailme")
@SuppressWarnings({"unused"})
public final class MailCmd extends CommandBase {

    private final static String BASE_PERM = "mailme.base.";
    private final static String ADMIN_PERM = "mailme.admin";

    private MailMe plugin;

    public MailCmd(MailMe plugin) {
        this.plugin = plugin;
    }

    @Permission(BASE_PERM)
    @Default
    public void execute(Player player) {
        player.sendMessage(plugin.getLocale().getLore(plugin.getPlayerDataHandler().getPlayerData(player).getLang(), "help"));
    }

    @Permission(BASE_PERM + "lang")
    @SubCommand("lang")
    @Completion("#locale")
    public void setLanguage(Player player, String lang) {
        lang = lang.toUpperCase();
        try {
            plugin.getPlayerDataHandler().getPlayerData(player.getUniqueId()).setLang(Locale.LANG.valueOf(lang));
        } catch (IllegalArgumentException e) {
            throw new SyntaxError();
        }
        player.sendMessage(plugin.getLocale().getMessage(Locale.LANG.valueOf(lang), "cmd.lang-change"));
    }

    @Permission(BASE_PERM + "notify")
    @SubCommand("notify")
    @Completion("#boolean")
    public void setNotifySetting(Player player, Boolean bool) {
        PlayerData data = plugin.getPlayerDataHandler().getPlayerData(player);
        data.setNotifySettings(bool);
        player.sendMessage(plugin.getLocale().getMessage(data.getLang(), "cmd.notify-change"));
    }

    @Permission(BASE_PERM + "read")
    @SubCommand("read")
    public void read(Player player) {
        new MailGui(plugin, player, plugin.getPlayerDataHandler().getPlayerData(player.getUniqueId()).getMail(), 0).open();
    }

    @Permission(BASE_PERM + "send")
    @SubCommand("send")
    @Alias("reply")
    public void send(Player player) {
        plugin.getGuiHandler().getChooseTypeGui().open(player);
    }

    @Permission(BASE_PERM + "text")
    @SubCommand("text")
    public void readAsText(Player player, @Optional Integer page) {

        PlayerData data = plugin.getPlayerDataHandler().getPlayerData(player.getUniqueId());
        Locale.LANG lang = data.getLang();

        if (page == null) page = 0;
        if (page > 0) {
            TextComponent message = new TextComponent(MailMe.getInstance().getLocale().getMessage(lang, "text.prev-page"));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(MailMe.getInstance().getLocale().getMessage(lang, "text.prev-page-hover")).create()));
            message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mailme text " + (page - 1)));
            player.spigot().sendMessage(message);
        }

        Pagination<Mail> pagination = new Pagination<>(2, plugin.getPlayerDataHandler().getPlayerData(player).getMail());

        if (page < pagination.totalPages()) {
            pagination.getPage(page).forEach(m -> player.spigot().sendMessage(m.getMailAsText()));

            TextComponent m = new TextComponent(MailMe.getInstance().getLocale().getMessage(lang, "text.next-page"));
            m.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(MailMe.getInstance().getLocale().getMessage(lang, "text.next-page-hover")).create()));
            m.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mailme text " + (page + 1)));
            player.spigot().sendMessage(m);
        }
    }

    @Permission(BASE_PERM + "mailbox")
    @SubCommand("mailbox")
    @Completion("#mailbox")
    public void mailBox(Player player, String string) {

        PlayerData data = plugin.getPlayerDataHandler().getPlayerData(player);

        if (string.equalsIgnoreCase("set")) {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                Block chest = player.getTargetBlock(null, 10);
                if (!chest.getType().equals(Material.CHEST)) {
                    player.sendMessage(plugin.getLocale().getMessage(data.getLang(), "mailbox.not-chest"));
                    return;
                }
                Location loc = chest.getLocation();

                Bukkit.getScheduler().runTask(plugin, () -> plugin.getPlayerDataHandler().getPlayerData(player).setMailBox(loc));
                player.sendMessage(plugin.getLocale().getMessage(data.getLang(), "mailbox.mailbox-set"));
            });
        } else if (string.equalsIgnoreCase("remove")) {
            data.setMailBox(null);
            player.sendMessage(plugin.getLocale().getMessage(data.getLang(), "mailbox.mailbox-removed"));
        }
    }

    @Permission(ADMIN_PERM)
    @SubCommand("defaultMB")
    public void setDefaultMailBox(Player player) {

        PlayerData data = plugin.getPlayerDataHandler().getPlayerData(player);
        ConfigurationSection mailboxConfig = plugin.getConfig().getConfigurationSection("default-mailbox");

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            Block chest = player.getTargetBlock(null, 10);
            if (!chest.getType().equals(Material.CHEST)) {
                player.sendMessage(plugin.getLocale().getMessage(data.getLang(), "mailbox.not-chest"));
                return;
            }
            Location loc = chest.getLocation();

            try (Stream<Path> walk = Files.walk(Paths.get(plugin.getDataFolder() + "/playerdata"))) {
                List<String> result = walk.map(Path::toString)
                        .filter(f -> f.endsWith(".json")).collect(Collectors.toList());

                result.forEach(file -> {
                    PlayerData playerData =  plugin.getPlayerDataHandler().getTmpFromFile(new File(file));
                    Location currentMailBox = playerData.getMailBox();
                    if (currentMailBox == null || currentMailBox.equals(plugin.defaultLocation)){ // If player's Mailbox was previously a default one or didn't have one before
                        playerData.setMailBox(loc); // Set MailBox to new location
                        plugin.getPlayerDataHandler().forceSetPlayerData(playerData);
                    }
                });


            } catch (IOException ignore) {
                plugin.debug("Set Default Mailbox: Failed to set mailboxes existing");
            }

            mailboxConfig.set("world", loc.getWorld().getName());
            mailboxConfig.set("x", loc.getBlockX());
            mailboxConfig.set("y", loc.getBlockY());
            mailboxConfig.set("z", loc.getBlockZ());
            plugin.saveConfig();

            plugin.defaultLocation = loc;

            player.sendMessage(plugin.getLocale().getMessage(data.getLang(), "mailbox.mailbox-set"));
        });
    }

    @Permission(ADMIN_PERM)
    @SubCommand("output-debug")
    public void outputDebug(CommandSender sender) {
        StringBuilder sb = new StringBuilder()
                .append("[MailMe Debug Output]")
                .append("\nBukkit version: ")
                .append(Bukkit.getServer().getBukkitVersion())
                .append("\nPlugin Version: ")
                .append(plugin.getDescription().getVersion())
                .append("\nJava Version: ")
                .append(System.getProperty("java.runtime.version"))
                .append("\nLoaded Language files: ")
                .append(plugin.getLocale().getLoadedLanguages());

        sender.sendMessage(sb.toString());
    }


}
