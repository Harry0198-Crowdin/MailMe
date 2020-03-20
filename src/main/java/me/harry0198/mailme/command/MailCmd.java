package me.harry0198.mailme.command;

import me.harry0198.mailme.MailMe;
import me.harry0198.mailme.components.IncompleteBuilderException;
import me.harry0198.mailme.datastore.PlayerData;
import me.harry0198.mailme.mail.Mail;
import me.harry0198.mailme.ui.ChooseTypeGui;
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
import org.bukkit.entity.Player;

@Command("mailme")
@Alias("mail")
@SuppressWarnings({"unused"})
public class MailCmd extends CommandBase {

    private MailMe plugin;

    public MailCmd(MailMe plugin) {
        this.plugin = plugin;
    }

    @Default
    public void execute(Player player) {

    }

    @SubCommand("lang")
    public void setLanguage(Player player, String lang) {
        lang = lang.toUpperCase();
        try {
            plugin.getPlayerDataHandler().getPlayerData(player.getUniqueId()).setLang(Locale.LANG.valueOf(lang));
        } catch (IllegalArgumentException e) {
            throw new SyntaxError();
        }
        player.sendMessage(plugin.getLocale().getMessage(Locale.LANG.valueOf(lang), "cmd.lang-change"));
    }

    @SubCommand("notify")
    public void setNotifySetting(Player player, Boolean bool) {
        PlayerData data = plugin.getPlayerDataHandler().getPlayerData(player);
        data.setNotifySettings(bool);
        player.sendMessage(plugin.getLocale().getMessage(data.getLang(), "cmd.notify-change"));
    }

    @SubCommand("read")
    public void execute2(Player player) {
        new MailGui(plugin, player, plugin.getPlayerDataHandler().getPlayerData(player.getUniqueId()).getMail(), 0).open();
    }

    @SubCommand("send")
    public void execute3(Player player) throws IncompleteBuilderException {
        plugin.getGuiHandler().getChooseTypeGui().open(player);
    }

    @SubCommand("text")
    public void execute4(Player player, @Optional Integer page) {

        PlayerData data = plugin.getPlayerDataHandler().getPlayerData(player.getUniqueId());
        Locale.LANG lang = data.getLang();

        if (page == null) page = 0;
        if (page > 0) {
            TextComponent message = new TextComponent(MailMe.getInstance().getLocale().getMessage(lang,"text.prev-page"));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(MailMe.getInstance().getLocale().getMessage(lang,"text.prev-page-hover")).create()));
            message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mailme text " + (page - 1)));
            player.spigot().sendMessage(message);
        }

        Pagination<Mail> pagination = new Pagination<>(2, plugin.getPlayerDataHandler().getPlayerData(player).getMail());

        if (page < pagination.totalPages()) {
            pagination.getPage(page).forEach(m -> player.spigot().sendMessage(m.getMailAsText()));

            TextComponent m = new TextComponent(MailMe.getInstance().getLocale().getMessage(lang,"text.next-page"));
            m.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(MailMe.getInstance().getLocale().getMessage(lang,"text.next-page-hover")).create()));
            m.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mailme text " + (page + 1)));
            player.spigot().sendMessage(m);
        }
    }

}
