/*
 *   Copyright [2020] [Harry0198]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.haroldstudios.mailme.mail.types;

import com.haroldstudios.mailme.mail.Mail;
import net.md_5.bungee.api.chat.*;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@SuppressWarnings({"unused"})
public final class MailMessages extends Mail {

    private String message;

    /**
     *
     * @param icon Icon ItemStack
     * @param message Message mail
     * @param recipients List of OfflinePlayers
     */
    public MailMessages(ItemStack icon, List<OfflinePlayer> recipients, String message) {
        this(icon, recipients, message, null);
    }

    /**
     *
     * @param icon Icon ItemStack
     * @param recipients List of OfflinePlayers
     * @param message Message mail
     * @param sender Sender of Mail
     */
    public MailMessages(ItemStack icon,  List<OfflinePlayer> recipients, String message, UUID sender) {
        this(icon, new Date(), recipients, message, sender);
    }

    /**
     *
     * @param icon Icon ItemStack
     * @param date Date to stamp onto mail
     * @param recipients List of OfflinePlayers
     * @param message Message mail
     */
    public MailMessages(ItemStack icon, Date date, List<OfflinePlayer> recipients, String message) {
        this(icon, date, recipients, message,null);
    }

    /**
     *
     * @param icon Icon ItemStack
     * @param date Date to stamp onto mail
     * @param recipients List of OfflinePlayers
     * @param message Message mail
     * @param sender Sender of Mail
     */
    public MailMessages(ItemStack icon, Date date, List<OfflinePlayer> recipients, String message, UUID sender) {
        super(icon, date);
        this.message = message;
        super.setSender(sender);
        super.addRecipients(recipients);
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
    public BaseComponent[] getContentsAsText() {
        return new ComponentBuilder(getMessage()).create();
    }

    @Override
    public void onClick(Player player) {
        player.spigot().sendMessage(getContentsAsText());
    }

    /* Setters */

    /**
     * Updates the mail's message
     *
     * @param message String message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
