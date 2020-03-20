package me.harry0198.mailme.conversations;

import me.harry0198.mailme.MailMe;
import me.harry0198.mailme.datastore.PlayerData;
import me.harry0198.mailme.mail.Mail;
import me.harry0198.mailme.ui.GuiHandler;
import me.harry0198.mailme.ui.MailGui;
import me.mattstudios.mfgui.gui.guis.Gui;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SearchInput extends StringPrompt {
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

        if (search == null) {
            Gui gui = new MailGui(MailMe.getInstance(), player, Collections.emptyList(), 0).getGui();
            gui.setItem(2, 5, new GuiItem(GuiHandler.getSearchIcon(MailMe.getInstance().getLocale(), data.getLang())));
            gui.open(player);
            return Prompt.END_OF_CONVERSATION;
        }

        List<Mail> mailList = data.getMail().stream().filter(mail -> mail.getSender().equals(search.getUniqueId())).collect(Collectors.toList());

        new MailGui(MailMe.getInstance(), player, mailList, 0).open();

        return Prompt.END_OF_CONVERSATION;
    }
}
