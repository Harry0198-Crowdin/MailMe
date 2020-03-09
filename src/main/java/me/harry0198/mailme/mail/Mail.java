package me.harry0198.mailme.mail;

import me.harry0198.mailme.utility.Utils;
import me.mattstudios.mfgui.gui.GuiItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

public abstract class Mail {

    private transient ItemStack icon;
    private Date date;

    public Mail(ItemStack icon) {
        this.icon = icon;
    }

    public abstract MailType getMailType();
    public abstract void getMail();

    /* Getters */

    public Date getDate() { return date; }

    public GuiItem getIcon() {
        return new GuiItem(icon, e -> e.getWhoClicked().sendMessage("Todo, send to next category"));
    }


    /* Setters */

    public void setIcon(ItemStack icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            oos.close();
            System.out.println("this one");

            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (IOException io) {
            System.out.println("io exception");
        }
        return null;
    }


    public enum MailType {
        MAIL_ITEM, MAIL_MESSAGE
    }
}

