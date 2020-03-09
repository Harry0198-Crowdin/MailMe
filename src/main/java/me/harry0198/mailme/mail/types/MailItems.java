package me.harry0198.mailme.mail.types;

import me.harry0198.mailme.mail.Mail;
import me.harry0198.mailme.utility.Utils;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.Date;

public final class MailItems extends Mail implements Serializable {

    public MailItems(ItemStack icon) {
        super(icon);
    }

    @Override
    public MailType getMailType() {
        return null;
    }

    @Override
    public void getMail() {

    }

}
