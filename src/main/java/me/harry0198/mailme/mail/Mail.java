package me.harry0198.mailme.mail;

import me.harry0198.mailme.MailMe;

import me.mattstudios.mfgui.gui.guis.GuiItem;
import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Mail {

    private ItemStack icon;
    private Date date;
    private List<UUID> recipients = new ArrayList<>();
    private boolean read = false;
    private boolean reply = false;
    private UUID sender;

    public Mail(ItemStack icon, Date date) {
        this.icon = icon;
        this.date = date;

    }

    public abstract MailType getMailType();
    public abstract void getMail();
    public abstract BaseComponent[] getContentsAsText();


    /* Getters */

    public Date getDate() { return date; }

    public GuiItem getIcon() {
        return new GuiItem(icon, e -> e.getWhoClicked().sendMessage("Todo, send to next category"));
    }

    public boolean isRead() {
        return read;
    }

    public boolean isReply() {
        return reply;
    }

    public UUID getSender() { return sender; }

    /* Setters */

    public void setIcon(ItemStack icon) {
        this.icon = icon;
    }

    public void setRead(boolean read) { this.read = read; }

    public void setReply(boolean reply) {
        this.reply = reply;
    }

    public void setSender(UUID uuid) { this.sender = uuid; }

    public void addRecipients(List<OfflinePlayer> players) {
        recipients.addAll(players.stream().map(OfflinePlayer::getUniqueId).collect(Collectors.toList()));
    }

    public void removeRecipient(OfflinePlayer player) {
        recipients.remove(player.getUniqueId());
    }

    protected void sendMail(Mail mail) {
        for (UUID player : recipients) {
            MailMe.getInstance().getPlayerDataHandler().getPlayerData(player).addMail(mail);
        }
        if (sender.toString().length() != 36) return;
        Bukkit.getPlayer(sender).sendMessage(String.format(MailMe.getInstance().getLocale().getMessage("notify.sent"), recipients.stream().map(pl -> Bukkit.getOfflinePlayer(pl).getName()).collect(Collectors.toList()).toString()));
    }


    public BaseComponent[] getMailAsText() {
        List<String> msgs = MailMe.getInstance().getLocale().getMessages("text.format");
        String sender = getSender() != null ? Bukkit.getOfflinePlayer(getSender()).getName() : "???";
        ComponentBuilder builder = new ComponentBuilder("");

        builder.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/mailme reply " + sender));
        builder.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(MailMe.getInstance().getLocale().getMessage("text.hover")).create()));

        for (String each : msgs) {
            String t = each + "\n";
            t = t.replaceAll("%time%", getDate().toString());
            t = t.replaceAll("%sender%", sender);
            if (!t.contains("%contents%")) {
                builder.append(new TextComponent(t));
                continue;
            }
            t = t.replaceAll("%contents%", "");
            builder.append(new TextComponent(t));

            builder.append(getContentsAsText());
        }

        return builder.create();
    }

    @Override
    public String toString() {
        return MailMe.GSON.toJson(this);
    }


    public enum MailType {
        MAIL_ITEM, MAIL_MESSAGE, MAIL_SOUND
    }
}

