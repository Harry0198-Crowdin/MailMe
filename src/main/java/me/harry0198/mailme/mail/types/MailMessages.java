package me.harry0198.mailme.mail.types;

import me.harry0198.mailme.MailMe;
import me.harry0198.mailme.mail.Mail;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import java.util.Date;
import java.util.List;

public final class MailMessages extends Mail {

    private final String type = "MailMessages";
    private String message;

    public MailMessages(ItemStack icon, String message, List<OfflinePlayer> recipients) {
        super(icon, new Date());
        this.message = message;
        recipients.forEach(r -> MailMe.getInstance().getPlayerDataHandler().getPlayerData(r).addMail(this));
    }

    public MailMessages(ItemStack icon, String message, Date date, List<OfflinePlayer> recipients) {
        super(icon, date);
        this.message = message;
        recipients.forEach(r -> MailMe.getInstance().getPlayerDataHandler().getPlayerData(r).addMail(this));
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
    public void getMail() {

    }

    @Override
    public TextComponent[] getMailAsText() {
        TextComponent message = new TextComponent( "Message" );
        message.setClickEvent( new ClickEvent( ClickEvent.Action.SUGGEST_COMMAND, "mailme reply" ) );
        message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.RED + "Click to Reply!").create() ) );

        return new TextComponent[]{message};
    }

    /* Setters */

    public void setMessage(String message) {
        this.message = message;
    }
}
