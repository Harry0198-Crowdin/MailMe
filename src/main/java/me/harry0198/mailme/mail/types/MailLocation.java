package me.harry0198.mailme.mail.types;

import me.harry0198.mailme.MailMe;
import me.harry0198.mailme.mail.Mail;
import me.mattstudios.mfgui.gui.guis.Gui;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public final class MailLocation extends Mail {

    private final String type = "MailLocation"; // For deserializer
    private Location location;

    /**
     *
     * @param icon Icon ItemStack
     * @param message Message mail
     * @param recipients List of OfflinePlayers
     */
    public MailLocation(ItemStack icon, List<OfflinePlayer> recipients, Location message) {
        this(icon, recipients, message, null);
    }

    /**
     *
     * @param icon Icon ItemStack
     * @param recipients List of OfflinePlayers
     * @param loc Message mail
     * @param sender Sender of Mail
     */
    public MailLocation(ItemStack icon,  List<OfflinePlayer> recipients, Location loc, UUID sender) {
        this(icon, new Date(), recipients, loc, sender);
    }

    /**
     *
     * @param icon Icon ItemStack
     * @param date Date to stamp onto mail
     * @param recipients List of OfflinePlayers
     * @param loc Message mail
     */
    public MailLocation(ItemStack icon, Date date, List<OfflinePlayer> recipients, Location loc) {
        this(icon, date, recipients, loc,null);
    }

    /**
     *
     * @param icon Icon ItemStack
     * @param date Date to stamp onto mail
     * @param recipients List of OfflinePlayers
     * @param loc Message mail
     * @param sender Sender of Mail
     */
    public MailLocation(ItemStack icon, Date date, List<OfflinePlayer> recipients, Location loc, UUID sender) {
        super(icon, date);
        recipients.forEach(r -> MailMe.getInstance().getPlayerDataHandler().getPlayerData(r).addMail(this));
        super.setSender(sender);
        location = loc;
    }
    
    @Override
    public MailType getMailType() {
        return MailType.MAIL_LOCATION;
    }

    @Override
    public Gui getMail() {
return null;
    }

    @Override
    public BaseComponent[] getContentsAsText() {
        return new ComponentBuilder(location.toString()).create();
    }
}
