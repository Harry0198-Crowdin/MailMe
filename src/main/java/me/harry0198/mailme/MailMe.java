package me.harry0198.mailme;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.harry0198.mailme.command.MailCmd;
import me.harry0198.mailme.conversations.ConversationAbandonedListener;
import me.harry0198.mailme.conversations.ConversationPrefix;
import me.harry0198.mailme.conversations.InputPrompt;
import me.harry0198.mailme.datastore.PlayerDataHandler;
import me.harry0198.mailme.mail.Mail;
import me.harry0198.mailme.mail.MailDeserializer;
import me.harry0198.mailme.ui.GuiHandler;
import me.harry0198.mailme.utility.Locale;

import me.mattstudios.mf.base.CommandManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;

public final class MailMe extends JavaPlugin {

    public static final Gson GSON = new GsonBuilder()
                    .setDateFormat("dd-M-yyyy hh:mm:ss")
                    .enableComplexMapKeySerialization()
                    .setPrettyPrinting()
                    .registerTypeAdapter(Mail.class, new MailDeserializer())
                    .create();
    private Locale locale;
    private GuiHandler uiHandler;
    private PlayerDataHandler playerDataHandler;

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
        new File(getDataFolder() + "/playerdata/").mkdir();
        this.locale = new Locale(YamlConfiguration.loadConfiguration(new File(getDataFolder() + "messages.yml")));
        this.uiHandler = new GuiHandler(this);
        saveDefaultConfig();

        this.playerDataHandler = new PlayerDataHandler(this);

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

    public PlayerDataHandler getPlayerDataHandler() { return playerDataHandler; }

    public ConversationFactory getConversationFactory() {
        return conversationFactory;
    }

    public static MailMe getInstance() {
        return getPlugin(MailMe.class);
    }
}
