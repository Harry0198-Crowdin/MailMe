package me.harry0198.mailme.database.playerdata;

import me.harry0198.mailme.mail.Mail;
import org.bukkit.entity.Player;

import java.util.*;

public final class PlayerData {

    private final Map<Date, Mail> mail = new TreeMap<>();

    public PlayerData(Player player) {

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

    /* Setters */

    /**
     * Adds Mail to Player's Map (Warning this skips the event). Otherwise use API method.
     *
     * @param mail Mail Object
     */
    public void addMail(Mail mail) {
        this.mail.put(mail.getDate(), mail);
    }
}
