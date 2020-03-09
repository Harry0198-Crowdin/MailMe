package me.harry0198.mailme.mail.types;

import me.harry0198.mailme.mail.Mail;
import org.bukkit.inventory.ItemStack;

import java.io.*;

public final class MailMessages extends Mail implements Serializable {

    private String message;

    public MailMessages(ItemStack icon, String message) {
        super(icon);
        this.message = message;
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

    /* Setters */

    public void setMessage(String message) {
        this.message = message;
    }
}
