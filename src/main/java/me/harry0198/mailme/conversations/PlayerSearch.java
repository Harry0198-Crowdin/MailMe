package me.harry0198.mailme.conversations;

import me.harry0198.mailme.MailMe;
import me.harry0198.mailme.datastore.PlayerData;
import me.harry0198.mailme.mail.Mail;
import me.harry0198.mailme.mail.MailBuilder;
import me.harry0198.mailme.ui.ChoosePlayerGui;
import me.harry0198.mailme.ui.GuiHandler;
import me.harry0198.mailme.ui.MailGui;
import me.mattstudios.mfgui.gui.components.ItemBuilder;
import me.mattstudios.mfgui.gui.guis.Gui;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;

public class PlayerSearch extends StringPrompt {
    @Override
    public String getPromptText(ConversationContext context) {
        PlayerData data = MailMe.getInstance().getPlayerDataHandler().getPlayerData((Player) context.getForWhom());
        return MailMe.getInstance().getLocale().getMessage(data.getLang(), "gui.search");
    }

    @Override
    public Prompt acceptInput(ConversationContext context, String s) {
        if (s.equals("cancel")) {
            return Prompt.END_OF_CONVERSATION;
        }

        PlayerData data = MailMe.getInstance().getPlayerDataHandler().getPlayerData((Player) context.getForWhom());

        Player player = (Player) context.getForWhom();
        OfflinePlayer search = Bukkit.getOfflinePlayer(s);
        System.out.println(search);
        if (search == null) {
            Gui gui = new ChoosePlayerGui(MailMe.getInstance(), player, Collections.emptyList(), 0).getGui();
            gui.setItem(2, 5, new GuiItem(GuiHandler.getSearchIcon(MailMe.getInstance().getLocale(), data.getLang())));
            gui.open(player);
            return Prompt.END_OF_CONVERSATION;
        }

        new ChoosePlayerGui(MailMe.getInstance(), player, new ArrayList<>(Collections.singletonList(search)), 0).open();

        return Prompt.END_OF_CONVERSATION;
    }
}
