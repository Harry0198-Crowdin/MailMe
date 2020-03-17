package me.harry0198.mailme.datastore;

import me.harry0198.mailme.MailMe;
import me.harry0198.mailme.mail.Mail;

import me.harry0198.mailme.utility.Locale;
import me.harry0198.mailme.utility.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

public final class PlayerData {

    private final TreeMap<Date,Mail> mail = new TreeMap<>(Collections.reverseOrder());
    private final MailMe plugin;
    private final File jsonData;
    private Locale.LANG lang;
    private UUID uuid;

    public PlayerData(final MailMe plugin, final UUID uuid) {
        this.plugin = plugin;
        this.uuid = uuid;
        jsonData = new File(plugin.getDataFolder() + "/playerdata/" + uuid.toString() + ".json");

        try {
            jsonData.createNewFile();
        } catch (IOException io) {
            io.printStackTrace();
        }

        this.mail.putAll(Utils.readJson(jsonData));
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
        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        if (player.isOnline()) {
            Player p = (Player) player;
            p.sendMessage(String.format(plugin.getLocale().getMessage("notify.received"), Bukkit.getOfflinePlayer(mail.getSender()).getName()));
        }
    }
}
