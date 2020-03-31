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

package me.harry0198.mailme;


import com.google.gson.*;
import me.harry0198.mailme.command.MailCmd;
import me.harry0198.mailme.conversations.*;
import me.harry0198.mailme.datastore.PlayerData;
import me.harry0198.mailme.datastore.PlayerDataHandler;
import me.harry0198.mailme.events.EntityEvents;
import me.harry0198.mailme.mail.Mail;
import me.harry0198.mailme.mail.MailSerializer;
import me.harry0198.mailme.ui.GuiHandler;
import me.harry0198.mailme.utility.ItemStackSerializer;
import me.harry0198.mailme.utility.Locale;

import me.harry0198.mailme.utility.Utils;
import me.mattstudios.mf.base.CommandManager;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

public final class MailMe extends JavaPlugin {

    public static final Gson GSON = new GsonBuilder()
                    .setDateFormat("dd-MM-yyyy hh:mm:ss.SSS")
                    .enableComplexMapKeySerialization()
                    .setPrettyPrinting()
                    .registerTypeAdapter(Mail.class, new MailSerializer())
                    .registerTypeAdapter(ItemStack.class, new ItemStackSerializer())
                    .serializeSpecialFloatingPointValues()
                    .create();
    private Locale locale;
    private GuiHandler uiHandler;
    private PlayerDataHandler playerDataHandler;
    private MailCmd mailCmds;

    /* Conversation Factory */
    private ConversationFactory conversationFactory;
    private ConversationFactory searchFactory;
    private ConversationFactory searchPlayerFactory;
    private ConversationFactory soundInputFactory;

    public static final List<Player> playerList = new ArrayList<>();

    private void loadConversations() {

        this.conversationFactory = new ConversationFactory(this).withModality(true)
                .withFirstPrompt(new InputPrompt())
                .withEscapeSequence("cancel").withTimeout(60)
                .addConversationAbandonedListener(new ConversationAbandonedListener())
                .thatExcludesNonPlayersWithMessage("Console is not supported by this command");
        this.searchFactory = new ConversationFactory(this).withModality(true)
                .withFirstPrompt(new SearchInput())
                .withEscapeSequence("cancel").withTimeout(60)
                .thatExcludesNonPlayersWithMessage("Console is not supported by this command");
        this.searchPlayerFactory = new ConversationFactory(this).withModality(true)
                .withFirstPrompt(new PlayerSearch())
                .withEscapeSequence("cancel").withTimeout(60)
                .thatExcludesNonPlayersWithMessage("Console is not supported by this command");
        this.soundInputFactory = new ConversationFactory(this).withModality(true)
                .withFirstPrompt(new SoundInput())
                .withEscapeSequence("cancel").withTimeout(60)
                .addConversationAbandonedListener(new ConversationAbandonedListener())
                .thatExcludesNonPlayersWithMessage("Console is not supported by this command");
    }

    @Override
    public void onEnable() {

        @SuppressWarnings("unused")
        Metrics metrics = new Metrics(this);

        loadConversations();
        saveDefaultConfig();
        debug("Creating playerdata folder");
        new File(getDataFolder() + "/playerdata").mkdir();
        debug("Loading locale");
        this.locale = new Locale();
        debug("Loading GuiHandler");
        this.uiHandler = new GuiHandler(this);
        debug("Registering events");
        getServer().getPluginManager().registerEvents(new EntityEvents(this), this);
        debug("Loading playerdata handler");
        this.playerDataHandler = new PlayerDataHandler(this);
        debug("Loading commands");
        CommandManager commandManager = new CommandManager(this);
        commandManager.getCompletionHandler().register("#locale", val -> Arrays.stream(Locale.LANG.values()).map(Enum::toString).collect(Collectors.toList()));
        commandManager.getCompletionHandler().register("#boolean", val -> Arrays.asList("true", "false"));
        commandManager.getCompletionHandler().register("#mailbox", val -> Arrays.asList("remove", "set"));
        this.mailCmds = new MailCmd(this);
        commandManager.register(mailCmds);
        commandManager.hideTabComplete(true);

        runMailBoxTask();
    }

    public BukkitTask runMailBoxTask() {

        return Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            // Iterate through players with mailbox
            for (Player player : playerList) {

                if (!player.isOnline()) continue;

                PlayerData data = getPlayerDataHandler().getPlayerData(player);
                if (data.getMailBox() == null) continue;

                Location loc = data.getMailBox();
                loc.add(0.5,1.2,0.5);

                List<Mail> unread = data.getMail().stream().filter(mail -> !mail.isRead()).collect(Collectors.toList());
                if (unread.size() > 0)
                    Utils.playNoteEffect(player, loc);
            }
        },0L,80L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void debug(String msg) {
        if (getConfig().getBoolean("debug"))
            getServer().getLogger().log(Level.INFO, msg);
    }

    public Locale getLocale() {
        return locale;
    }

    public GuiHandler getGuiHandler() {
        return uiHandler;
    }

    public PlayerDataHandler getPlayerDataHandler() { return playerDataHandler; }

    public ConversationFactory getConversationFactory() {
        return conversationFactory;
    }

    public ConversationFactory getSearchFactory() {
        return searchFactory;
    }

    public ConversationFactory getSearchPlayerFactory() { return searchPlayerFactory; }

    public ConversationFactory getSoundInputFactory() {
        return soundInputFactory;
    }

    public MailCmd getCmds() {
        return mailCmds;
    }

    public static MailMe getInstance() {
        return getPlugin(MailMe.class);
    }
}
