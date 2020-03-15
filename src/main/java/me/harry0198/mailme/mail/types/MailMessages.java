package me.harry0198.mailme.mail.types;

import me.harry0198.mailme.MailMe;
import me.harry0198.mailme.mail.Mail;
import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public final class MailMessages extends Mail {

    @SuppressWarnings({"unused"})
    private final String type = "MailMessages"; // For deserializer
    private String message;

    /**
     *
     * @param icon Icon ItemStack
     * @param message Message mail
     * @param recipients List of OfflinePlayers
     */
    public MailMessages(ItemStack icon, List<OfflinePlayer> recipients, String message) {
        this(icon, recipients, message, null);
    }

    /**
     *
     * @param icon Icon ItemStack
     * @param recipients List of OfflinePlayers
     * @param message Message mail
     * @param sender Sender of Mail
     */
    public MailMessages(ItemStack icon,  List<OfflinePlayer> recipients, String message, UUID sender) {
        this(icon, new Date(), recipients, message, sender);
    }

    /**
     *
     * @param icon Icon ItemStack
     * @param date Date to stamp onto mail
     * @param recipients List of OfflinePlayers
     * @param message Message mail
     */
    public MailMessages(ItemStack icon, Date date, List<OfflinePlayer> recipients, String message) {
        this(icon, date, recipients, message,null);
    }

    /**
     *
     * @param icon Icon ItemStack
     * @param date Date to stamp onto mail
     * @param recipients List of OfflinePlayers
     * @param message Message mail
     * @param sender Sender of Mail
     */
    public MailMessages(ItemStack icon, Date date, List<OfflinePlayer> recipients, String message, UUID sender) {
        super(icon, date);
        this.message = message;
        recipients.forEach(r -> MailMe.getInstance().getPlayerDataHandler().getPlayerData(r).addMail(this));
        super.setSender(sender);
    }

    /* Getters */

    public String getMessage() {
        return message;
    }

    @Override
    public MailType getMailType() {
        return MailType.MAIL_MESSAGE;
    }

    @Override
    public BaseComponent[] getContentsAsText() {
        return new ComponentBuilder(getMessage()).create();
    }

    @Override
    public void getMail() {

    }

    /* Setters */

    public void setMessage(String message) {
        this.message = message;
    }
}
