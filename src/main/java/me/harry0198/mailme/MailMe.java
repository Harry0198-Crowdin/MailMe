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
import me.harry0198.mailme.datastore.PlayerDataHandler;
import me.harry0198.mailme.events.PlayerJoinEvent;
import me.harry0198.mailme.mail.Mail;
import me.harry0198.mailme.mail.MailSerializer;
import me.harry0198.mailme.ui.GuiHandler;
import me.harry0198.mailme.utility.ItemStackSerializer;
import me.harry0198.mailme.utility.Locale;

import me.mattstudios.mf.base.CommandManager;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.util.Arrays;
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
        loadConversations();
        saveDefaultConfig();
        new File(getDataFolder() + "/playerdata").mkdir();
        this.locale = new Locale();
        this.uiHandler = new GuiHandler(this);
        getServer().getPluginManager().registerEvents(new PlayerJoinEvent(this), this);

        this.playerDataHandler = new PlayerDataHandler(this);

        CommandManager commandManager = new CommandManager(this);
        commandManager.getCompletionHandler().register("#locale", val -> Arrays.stream(Locale.LANG.values()).map(Enum::toString).collect(Collectors.toList()));
        commandManager.getCompletionHandler().register("#boolean", val -> Arrays.asList("true", "false"));
        this.mailCmds = new MailCmd(this);
        commandManager.register(mailCmds);
        commandManager.hideTabComplete(true);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
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
