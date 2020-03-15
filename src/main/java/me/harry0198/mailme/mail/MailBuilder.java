package me.harry0198.mailme.mail;

import me.harry0198.mailme.components.IncompleteBuilderException;
import me.harry0198.mailme.mail.types.MailItems;
import me.harry0198.mailme.mail.types.MailMessages;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

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

        public List<OfflinePlayer> getRecipients() {
            return recipients;
        }

        public Mail build() throws IncompleteBuilderException {

            if (icon == null) throw new IncompleteBuilderException("Icon has not been set!");

            mailDrafts.remove(sender);

            switch (mailType) {
                case MAIL_ITEM:

                    return new MailItems(icon, recipients, items, sender.getUniqueId());
                case MAIL_MESSAGE:
                    return new MailMessages(icon, recipients, message, sender.getUniqueId());

            }
            return null;
        }
    }

    private MailBuilder() {}
}
