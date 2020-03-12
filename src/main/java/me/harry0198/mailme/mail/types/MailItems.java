package me.harry0198.mailme.mail.types;

import me.harry0198.mailme.mail.Mail;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.Date;

public final class MailItems extends Mail implements Serializable {

    public MailItems(ItemStack icon) {
        super(icon, new Date());
    }

    @Override
    public MailType getMailType() {
        return null;
    }

    @Override
    public void getMail() {

    }

    @Override
    public TextComponent[] getMailAsText() {
        return null;
    }

}
