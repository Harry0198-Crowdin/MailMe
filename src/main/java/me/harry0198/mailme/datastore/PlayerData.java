package me.harry0198.mailme.datastore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.harry0198.mailme.MailMe;
import me.harry0198.mailme.components.IncompleteBuilderException;
import me.harry0198.mailme.mail.Mail;

import me.harry0198.mailme.mail.MailBuilder;
import me.harry0198.mailme.utility.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.*;

public final class PlayerData {

    private final TreeMap<Date, Mail> mail;
    private final MailMe plugin;
    private final File jsonData;


    public PlayerData(final MailMe plugin, final UUID uuid) {
        this.plugin = plugin;
        jsonData = new File(plugin.getDataFolder() + "/playerdata/" + uuid.toString() + ".json");

        try {
            jsonData.createNewFile();
        } catch (IOException io) {
            io.printStackTrace();
        }

        this.mail = Utils.readJson(jsonData);

        try {
            addMail(new MailBuilder.Builder(Mail.MailType.MAIL_MESSAGE, Bukkit.getPlayer(uuid)).setIcon(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)).setMessage("M").build());
        } catch (IncompleteBuilderException e) {
            e.printStackTrace();
        }
    }

    /* Getters */

    /**
     * Gets Mail Objects in order - newest to oldest
     *
     * @return List<Mail> Player's mail newest to oldest
     */
    public List<Mail> getMail() {
         return new ArrayList<>(mail.values());
    }

    /**
     * Gets amount of Mail player has
     *
     * @return Integer Amount of Mail player has
     */
    public Integer getMailCount() {
        return mail.values().size();
    }

    public File getFile() {
        return jsonData;
    }

    /* Setters */

    /**
     * Adds Mail to Player's Map (Warning this skips the event). Otherwise use API method.
     *
     * @param mail Mail Object
     */
    public void addMail(Mail mail) {
        this.mail.put(mail.getDate(), mail);
        Utils.writeJson(jsonData, this.mail);
    }
}
