package me.harry0198.mailme.mail;

import me.harry0198.mailme.MailMe;

import me.harry0198.mailme.datastore.PlayerData;
import me.mattstudios.mfgui.gui.guis.Gui;
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
    private final int delay;

    public Mail(ItemStack icon, Date date) {
        this.icon = icon;
        this.date = date;
        delay = MailMe.getInstance().getConfig().getInt("delay");
    }

    public abstract MailType getMailType();
    public abstract Gui getMail();
    public abstract BaseComponent[] getContentsAsText();


    /* Getters */

    public Date getDate() { return date; }

    public ItemStack getIcon() {
        return icon;
    }

    public boolean isRead() {
        return read;
    }

    public boolean isReply() {
        return reply;
    }

    public UUID getSender() { return sender; }

    public List<UUID> getRecipients() { return recipients; }

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

    public void sendMail() {
        UUID sender = getSender();
        List<UUID> recipients = getRecipients();
        List<UUID> newList = new ArrayList<>(recipients);

        PlayerData senderData = MailMe.getInstance().getPlayerDataHandler().getPlayerData(sender);
        for (UUID player : recipients) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, -delay);
            Date timeTravel = cal.getTime();
            PlayerData data = MailMe.getInstance().getPlayerDataHandler().getPlayerData(player);
            List<Mail> dataMail = data.getMail().stream().filter(r -> r.getDate().getTime() > timeTravel.getTime()).filter(r -> r.getSender().equals(sender)).collect(Collectors.toList());
            if (!dataMail.isEmpty()) {
                Bukkit.getPlayer(sender).sendMessage(String.format(MailMe.getInstance().getLocale().getMessage(senderData.getLang(), "notify.could-not-send"), Bukkit.getOfflinePlayer(player).getName()));
                newList.remove(player);
            }
        }
        if (sender.toString().length() != 36) return;
        Bukkit.getPlayer(sender).sendMessage(String.format(MailMe.getInstance().getLocale().getMessage(senderData.getLang(), "notify.sent"), newList.stream().map(pl -> Bukkit.getOfflinePlayer(pl).getName()).collect(Collectors.toList()).toString()));
        newList.forEach(player -> MailMe.getInstance().getPlayerDataHandler().getPlayerData(player).addMail(this));
    }


    public BaseComponent[] getMailAsText() {
        List<String> msgs = MailMe.getInstance().getLocale().getMessages("text.format");
        String sender = getSender() != null ? Bukkit.getOfflinePlayer(getSender()).getName() : "???";
        ComponentBuilder builder = new ComponentBuilder("");

        builder.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/mailme reply"));
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

    public enum MailType {
        MAIL_ITEM, MAIL_MESSAGE, MAIL_SOUND, MAIL_LOCATION
    }
}

