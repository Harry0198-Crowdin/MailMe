package me.harry0198.mailme.mail.types;

import me.harry0198.mailme.mail.Mail;
import me.harry0198.mailme.utility.NMSReflection;
import net.md_5.bungee.api.chat.*;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

import java.util.Date;
import java.util.List;

public final class MailSound extends Mail {

    @SuppressWarnings({"unused"})
    private final String type = "MailSound"; // For deserializer
    private Sound sound;
    private MailMessages messages; // Nested Message in Sound

    public MailSound(ItemStack icon, List<OfflinePlayer> recipients, Sound sound) {
        this(icon, recipients, sound, "");
    }

    public MailSound(final ItemStack icon, final List<OfflinePlayer> recipients, final Sound sound, final String msg) {
        super(icon, new Date());
        super.addRecipients(recipients);
        this.sound = sound;
        messages = new MailMessages(icon, recipients, msg);
        super.sendMail(this);
    }


    @Override
    public MailType getMailType() {
        return null;
    }

    @Override
    public void getMail() {

    }

    @Override
    public BaseComponent[] getContentsAsText() {
        ComponentBuilder builder = new ComponentBuilder("");
        TextComponent txt = new TextComponent(sound.toString() + "\n");
        TextComponent msg = new TextComponent(messages.getMessage());
        builder.append(txt);
        builder.append(msg);
        return builder.create();
    }
}
