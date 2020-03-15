package me.harry0198.mailme.command;

import com.google.gson.reflect.TypeToken;
import me.harry0198.mailme.MailMe;
import me.harry0198.mailme.mail.Mail;
import me.harry0198.mailme.utility.ItemStackSerializer;
import me.harry0198.mailme.utility.Pagination;
import me.mattstudios.mf.annotations.*;
import me.mattstudios.mf.annotations.Optional;
import me.mattstudios.mf.base.CommandBase;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;


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

        // new GUI(plugin, 5, "").fillBetweenPoints(2,2,4,8, Collections.singletonList(new GuiItem(new ItemStack(Material.EMERALD)))).open(player);
    }

    @SubCommand("read")
    public void execute2(Player player) throws IOException, ClassNotFoundException {

        ItemStack stack = new ItemStack(Material.STONE);
        byte[] json = ItemStackSerializer.toByteArrayItemStackArray(new ItemStack[]{stack});
        System.out.println(json);
        ItemStack[] sta = ItemStackSerializer.fromByteArrayItemStackArray(json, 1);
        System.out.println(sta);
    }

    @SubCommand("send")
    public void execute3(Player player) {

    }

    @SubCommand("text")
    public void execute4(Player player, @Optional Integer page) {
        if (page == null) page = 0;
        if (page > 0) {
            TextComponent message = new TextComponent(MailMe.getInstance().getLocale().getMessage("text.prev-page"));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(MailMe.getInstance().getLocale().getMessage("text.prev-page-hover")).create()));
            message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mailme text " + (page - 1)));
            player.spigot().sendMessage(message);
        }

        Pagination<Mail> pagination = new Pagination<>(2, plugin.getPlayerDataHandler().getPlayerData(player).getMail());

        if (page < pagination.totalPages()) {
            pagination.getPage(page).forEach(m -> player.spigot().sendMessage(m.getMailAsText()));

            TextComponent m = new TextComponent(MailMe.getInstance().getLocale().getMessage("text.next-page"));
            m.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(MailMe.getInstance().getLocale().getMessage("text.next-page-hover")).create()));
            m.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mailme text " + (page + 1)));
            player.spigot().sendMessage(m);
        }
    }

}
