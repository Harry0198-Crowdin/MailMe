package me.harry0198.mailme.datastore;

import me.harry0198.mailme.MailMe;
import me.harry0198.mailme.mail.Mail;

import me.harry0198.mailme.utility.Locale;
import me.harry0198.mailme.utility.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;

public final class PlayerData {

    private final TreeMap<Date,Mail> mail = new TreeMap<>(Collections.reverseOrder());
    private Locale.LANG lang;
    private UUID uuid;
    private boolean notify = true;

    public PlayerData(final UUID uuid, final Locale.LANG lang) {
        this.uuid = uuid;
        this.lang = lang;
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

    /**
     * Gets the language the player has set
     *
     * @return Locale Language player is using
     */
    public Locale.LANG getLang() { return this.lang; }

    public boolean getNotifySetting() { return notify; }


    /* Setters */

    public void setNotifySettings(boolean notify) {
        this.notify = notify;
        Utils.writeJson(new File(MailMe.getInstance().getDataFolder() + "/playerdata/" + uuid.toString() + ".json"), this);
    }

    /**
     * Adds Mail to Player's Map
     *
     * @param mail Mail Object
     */
    public void addMail(Mail mail) {
        this.mail.put(mail.getDate(), mail);
        Utils.writeJson(new File(MailMe.getInstance().getDataFolder() + "/playerdata/" + uuid.toString() + ".json"), this);

        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        if (player.isOnline() && notify) {
            Player p = (Player) player;
            p.sendMessage(String.format(MailMe.getInstance().getLocale().getMessage(getLang(), "notify.received"), Bukkit.getOfflinePlayer(mail.getSender()).getName()));
        }
    }

    public void setLang(Locale.LANG lang) {
        this.lang = lang;
        Utils.writeJson(new File(MailMe.getInstance().getDataFolder() + "/playerdata/" + uuid.toString() + ".json"), this);
    }
}
