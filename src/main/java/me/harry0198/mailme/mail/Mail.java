package me.harry0198.mailme.mail;

import me.harry0198.mailme.MailMe;
import me.mattstudios.mfgui.gui.GuiItem;
import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Mail {

    private ItemStack icon;
    private Date date;
    private List<UUID> recipients;
    private boolean read = false;
    private boolean reply = false;
    private UUID sender;
    private transient TextComponent prevPage;
    private transient TextComponent nextPage;


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

    public TextComponent getMailAsText() {
        List<String> msgs = MailMe.getInstance().getLocale().getMessages("text.format");
        String sender = getSender() != null ? Bukkit.getOfflinePlayer(getSender()).getName() : "???";
        TextComponent message = new TextComponent();
        message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/mailme reply " + sender));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(MailMe.getInstance().getLocale().getMessage("text.hover")).create()));
        String fullMsg = "";

        for (String each : msgs) {
            String t = each;
            t = t.replaceAll("%time%", getDate().toString());
            t = t.replaceAll("%sender%", sender);
            t = t.replaceAll("%contents%", Arrays.toString(getContentsAsText()));
            fullMsg = fullMsg + t + "\n";
        }
        message.setText(fullMsg);
        return message;
    }

    @Override
    public String toString() {
        return MailMe.GSON.toJson(this);
    }


    public enum MailType {
        MAIL_ITEM, MAIL_MESSAGE
    }
}

