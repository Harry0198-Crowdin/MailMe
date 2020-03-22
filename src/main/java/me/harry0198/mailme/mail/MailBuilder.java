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

package me.harry0198.mailme.mail;

import me.harry0198.mailme.components.IncompleteBuilderException;
import me.harry0198.mailme.mail.types.MailItems;
import me.harry0198.mailme.mail.types.MailLocation;
import me.harry0198.mailme.mail.types.MailMessages;
import me.harry0198.mailme.mail.types.MailSound;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@SuppressWarnings("unused")
public final class MailBuilder {

    private static final Map<Player, Builder> mailDrafts = new HashMap<>();

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

        public Builder(Mail.MailType type, Player sender) {
            this.sender = sender;
            this.mailType = type;
            this.recipients = new ArrayList<>();
            this.items = new ArrayList<>();
            mailDrafts.put(sender, this);
        }

        public Builder addRecipient(OfflinePlayer player) {
            recipients.add(player);
            mailDrafts.put(sender, this);
            return this;
        }

        public Builder removeRecipient(OfflinePlayer player) {
            recipients.remove(player);
            mailDrafts.put(sender, this);
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            mailDrafts.put(sender, this);
            return this;
        }

        public Builder setIcon(ItemStack stack) {
            this.icon = stack;
            mailDrafts.put(sender, this);
            return this;
        }

        public Builder addItems(List<ItemStack> items) {
            this.items.addAll(items);
            mailDrafts.put(sender, this);
            return this;
        }

        public Builder addItem(ItemStack item) {
            this.items.add(item);
            mailDrafts.put(sender, this);
            return this;
        }

        public Builder setSound(Sound sound) {
            this.sound = sound;
            mailDrafts.put(sender, this);
            return this;
        }

        public Builder setLocation(Location location) {
            this.location = location;
            mailDrafts.put(sender, this);
            return this;
        }

        public Mail.MailType getMailType() {
            return mailType;
        }

        public List<OfflinePlayer> getRecipients() {
            return recipients;
        }

        public Mail build() throws IncompleteBuilderException {

            if (icon == null) throw new IncompleteBuilderException("Icon has not been set!");
            if (recipients.isEmpty()) throw new IncompleteBuilderException("You must add recipients!");
            mailDrafts.remove(sender);

            switch (mailType) {
                case MAIL_ITEM:

                    return new MailItems(icon, recipients, items, sender.getUniqueId());
                case MAIL_MESSAGE:
                    return new MailMessages(icon, recipients, message, sender.getUniqueId());
                case MAIL_SOUND:
                    if (sound == null) throw new IncompleteBuilderException("Sound is null for Sound Mail!");
                    return new MailSound(icon, recipients, sound, message, sender.getUniqueId());
                case MAIL_LOCATION:
                    if (location == null) throw new IncompleteBuilderException("Location is null for Location Mail!");
                    return new MailLocation(icon, recipients, location, sender.getUniqueId());


            }
            return null;
        }
    }

    private MailBuilder() {}
}
