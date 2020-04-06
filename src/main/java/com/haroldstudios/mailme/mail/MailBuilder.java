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

package com.haroldstudios.mailme.mail;

import com.google.gson.reflect.TypeToken;
import com.haroldstudios.mailme.MailMe;
import com.haroldstudios.mailme.components.IncompleteBuilderException;
import com.haroldstudios.mailme.mail.types.MailItems;
import com.haroldstudios.mailme.mail.types.MailLocation;
import com.haroldstudios.mailme.mail.types.MailMessages;
import com.haroldstudios.mailme.mail.types.MailSound;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;
import java.util.*;

@SuppressWarnings("unused")
public final class MailBuilder {

    public static final Map<Player, Builder> mailDrafts = new HashMap<>();

    public static Builder getMailDraft(Player player) { return mailDrafts.get(player); }

    public final static class Builder {

        private Mail.MailType mailType;
        private Player sender;
        private String message;
        private ItemStack icon;
        private List<OfflinePlayer> recipients;
        private List<ItemStack> items;
        private Sound sound;
        private Location location;
        private boolean preset;
        private String presetName;

        /**
         * MailBuilder Class Constructor
         * Create using new MailBuilder.Builder(type, sender)
         *
         * @param type MailType enum to use {@link Mail.MailType}
         * @param sender Player who is sending the mail
         */
        public Builder(Mail.MailType type, Player sender) {
            this.sender = sender;
            this.mailType = type;
            this.recipients = new ArrayList<>();
            this.items = new ArrayList<>();
            mailDrafts.put(sender, this);
        }

        /**
         * Sets a preset mail for sending
         *
         * @param preset Preset
         * @return The Builder
         */
        public Builder setPreset(boolean preset) {
            this.preset = preset;
            mailDrafts.put(sender, this);
            return this;
        }

        /**
         * Sets a preset mail for sending
         *
         * @param name Preset name
         * @return The Builder
         */
        public Builder setPresetName(String name) {
            this.presetName = name;
            mailDrafts.put(sender, this);
            return this;
        }

        /**
         * Sets the mail type
         *
         * @param type MailType
         * @return The Builder
         */
        public Builder setMailType(Mail.MailType type) {
            this.mailType = type;
            mailDrafts.put(sender, this);
            return this;
        }

        /**
         * Adds a single recipient to the mail
         *
         * @param player OfflinePlayer to send to
         * @return The Builder
         */
        public Builder addRecipient(OfflinePlayer player) {
            recipients.add(player);
            mailDrafts.put(sender, this);
            return this;
        }

        /**
         * Adds a list of recipients to the mail object
         *
         * @param players List of OfflinePlayers
         * @return The Builder
         */
        public Builder addRecipients(List<OfflinePlayer> players) {
            recipients.addAll(players);
            mailDrafts.put(sender, this);
            return this;
        }

        /**
         * Adds an array of recipients to the mail object
         *
         * @param players Array of OfflinePlayers
         * @return The Builder
         */
        public Builder addRecipients(OfflinePlayer... players) {
            recipients.addAll(Arrays.asList(players));
            mailDrafts.put(sender, this);
            return this;
        }

        /**
         * Removes a single recipient from the mail
         *
         * @param player OfflinePlayer to remove from mail
         * @return The Builder
         */
        public Builder removeRecipient(OfflinePlayer player) {
            recipients.remove(player);
            mailDrafts.put(sender, this);
            return this;
        }

        /**
         * Sets a message for the MailMessages type enum (Message sending)
         *
         * @param message String to send
         * @return The Builder
         */
        public Builder setMessage(String message) {
            this.message = message;
            mailDrafts.put(sender, this);
            return this;
        }

        /**
         * Allows for updating the sender of the mail
         *
         * @param sender Player to set - null = server
         * @return The Builder
         */
        public Builder setSender(Player sender) {
            this.sender = sender;
            mailDrafts.put(sender, this);
            return this;
        }

        /**
         * Sets the icon of the mail
         *
         * @param stack ItemStack icon
         * @return The Builder
         */
        public Builder setIcon(ItemStack stack) {
            this.icon = stack;
            mailDrafts.put(sender, this);
            return this;
        }

        /**
         * Adds items for the MailItems type enum (Item sending)
         *
         * @param items List of items to send
         * @return The Builder
         */
        public Builder addItems(List<ItemStack> items) {
            this.items.addAll(items);
            mailDrafts.put(sender, this);
            return this;
        }

        /**
         * Adds a single item for the MailItems type enum (Item sending)
         *
         * @param item ItemStack to send
         * @return The Builder
         */
        public Builder addItem(ItemStack item) {
            this.items.add(item);
            mailDrafts.put(sender, this);
            return this;
        }

        /**
         * Sets the sound for the MailSound type enum (Sound sending)
         *
         * @param sound Sound to send
         * @return The Builder
         */
        public Builder setSound(Sound sound) {
            this.sound = sound;
            mailDrafts.put(sender, this);
            return this;
        }

        /**
         * Sets the location for the MailLocation type enum (Location sending)
         *
         * @param location Location to send
         * @return The Builder
         */
        public Builder setLocation(Location location) {
            this.location = location;
            mailDrafts.put(sender, this);
            return this;
        }

        /**
         * Getter for the MailType
         *
         * @return {@link Mail.MailType}
         */
        public Mail.MailType getMailType() {
            return mailType;
        }

        /**
         * Getter for the recipients of mail
         *
         * @return List of OfflinePlayers for recipients
         */
        public List<OfflinePlayer> getRecipients() {
            return recipients;
        }

        public List<ItemStack> getItems() { return items; }

        public boolean isPreset() {
            return preset;
        }

        /**
         * Creates the mail object dependent on the MailType enum used
         *
         * @return The Mail Object
         * @throws IncompleteBuilderException if there are missing values
         */
        public Mail build() throws IncompleteBuilderException {

            if (icon == null) throw new IncompleteBuilderException("Icon has not been set!");
            if (recipients.isEmpty()) throw new IncompleteBuilderException("You must add recipients!");

            Mail mail = null;

            switch (mailType) {
                case MAIL_ITEM:
                    if (items.isEmpty()) throw new IncompleteBuilderException("Items can't be empty!");
                    mail = new MailItems(icon, recipients, items, sender.getUniqueId());
                    break;
                case MAIL_MESSAGE:
                    if (message == null) throw new IncompleteBuilderException("You must set a message!");
                    mail = new MailMessages(icon, recipients, message, sender.getUniqueId());
                    break;
                case MAIL_SOUND:
                    if (sound == null) throw new IncompleteBuilderException("Sound is null for Sound Mail!");
                    mail =  new MailSound(icon, recipients, sound, message, sender.getUniqueId());
                    break;
                case MAIL_LOCATION:
                    if (location == null) throw new IncompleteBuilderException("Location is null for Location Mail!");
                    mail = new MailLocation(icon, recipients, location, sender.getUniqueId());
                    break;


            }

            if (mail != null && isPreset()) {
                mail.clearRecipients();
                Type token = new TypeToken<Mail>() {}.getType();
                MailMe.getInstance().getConfig().set("presets." + presetName, MailMe.GSON.toJson(mail, token));
                MailMe.getInstance().saveConfig();
            }

            return mail;
        }
    }

    // Forces use of Builder Pattern
    private MailBuilder() {}
}
