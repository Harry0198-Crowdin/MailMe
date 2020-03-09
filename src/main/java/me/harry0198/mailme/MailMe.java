package me.harry0198.mailme;


import me.harry0198.mailme.command.MailCmd;
import me.harry0198.mailme.conversations.ConversationAbandonedListener;
import me.harry0198.mailme.conversations.ConversationPrefix;
import me.harry0198.mailme.conversations.InputPrompt;
import me.harry0198.mailme.ui.GuiHandler;
import me.harry0198.mailme.utility.Locale;
import me.mattstudios.mf.base.CommandManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.conversations.ConversationFactory;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class MailMe extends JavaPlugin {

    private Locale locale;
    private GuiHandler uiHandler;

    /* Conversation Factory */
    private ConversationFactory conversationFactory;

    /**
     * Class constructor -- loads the conversation factory
     */
    public MailMe() {
        this.conversationFactory = new ConversationFactory(this).withModality(true)
                .withPrefix(new ConversationPrefix()).withFirstPrompt(new InputPrompt())
                .withEscapeSequence("cancel").withTimeout(60)
                .thatExcludesNonPlayersWithMessage("Console is not supported by this command")
                .addConversationAbandonedListener(new ConversationAbandonedListener());
    }

    @Override
    public void onEnable() {
        this.locale = new Locale(YamlConfiguration.loadConfiguration(new File(getDataFolder() + "messages.yml")));
        this.uiHandler = new GuiHandler(this);

        CommandManager commandManager = new CommandManager(this);
        commandManager.register(new MailCmd(this));
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

    public ConversationFactory getConversationFactory() {
        return conversationFactory;
    }

    public static MailMe getInstance() {
        return getPlugin(MailMe.class);
    }
}
