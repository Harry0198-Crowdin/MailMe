package me.harry0198.mailme.mail.types;

import me.harry0198.mailme.mail.Mail;
import me.harry0198.mailme.utility.NMSReflection;
import me.mattstudios.mfgui.gui.guis.Gui;
import net.md_5.bungee.api.chat.*;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public final class MailSound extends Mail {

    @SuppressWarnings({"unused"})
    private final String type = "MailSound"; // For deserializer
    private Sound sound;
    private MailMessages messages; // Nested Message in Sound

    public MailSound(ItemStack icon, List<OfflinePlayer> recipients, Sound sound) {
        this(icon, recipients, sound, "", null);
    }

    public MailSound(final ItemStack icon, final List<OfflinePlayer> recipients, final Sound sound, final String msg, final UUID sender) {
            super(icon, new Date());
            super.addRecipients(recipients);
            this.sound = sound;
            messages = new MailMessages(icon, recipients, msg);
            super.setSender(sender);
    }


    @Override
    public MailType getMailType() {
        return MailType.MAIL_SOUND;
    }

    @Override
    public Gui getMail() {
return null;
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
