package me.harry0198.mailme.mail;

import me.mattstudios.mfgui.gui.GuiItem;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Base64;
import java.util.Date;
import java.util.List;

public abstract class Mail {

    private ItemStack icon;
    private Date date;
    private List<OfflinePlayer> recipients;
    private boolean read = false;
    private boolean reply = false;

    public Mail(ItemStack icon, Date date) {
        this.icon = icon;
        this.date = date;
    }

    public abstract MailType getMailType();
    public abstract void getMail();
    public abstract TextComponent[] getMailAsText();

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

    /* Setters */

    public void setIcon(ItemStack icon) {
        this.icon = icon;
    }

    public void setRead(boolean read) { this.read = read; }

    public void setReply(boolean reply) {
        this.reply = reply;
    }

    @Override
    public String toString() {
        try {
            //TODO gson each field
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            oos.close();
            System.out.println("this one");

            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (IOException io) {
            io.printStackTrace();
            return null;
        }
    }


    public enum MailType {
        MAIL_ITEM, MAIL_MESSAGE
    }
}

