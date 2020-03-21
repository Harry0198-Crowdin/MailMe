package me.harry0198.mailme;


import com.google.gson.*;
import me.harry0198.mailme.command.MailCmd;
import me.harry0198.mailme.conversations.ConversationAbandonedListener;
import me.harry0198.mailme.conversations.InputPrompt;
import me.harry0198.mailme.conversations.PlayerSearch;
import me.harry0198.mailme.conversations.SearchInput;
import me.harry0198.mailme.datastore.PlayerDataHandler;
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

    /**
     * Class constructor -- loads the conversation factory
     */
    public MailMe() {

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
    }

    @Override
    public void onEnable() {
        new File(getDataFolder() + "/playerdata/").mkdir();
        this.locale = new Locale();
        this.uiHandler = new GuiHandler(this);
        saveDefaultConfig();

        this.playerDataHandler = new PlayerDataHandler(this);

        CommandManager commandManager = new CommandManager(this);
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

    public MailCmd getCmds() {
        return mailCmds;
    }

    public static MailMe getInstance() {
        return getPlugin(MailMe.class);
    }
}
