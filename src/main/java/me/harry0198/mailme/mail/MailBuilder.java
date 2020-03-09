package me.harry0198.mailme.mail;

import me.harry0198.mailme.mail.types.MailItems;
import me.harry0198.mailme.mail.types.MailMessages;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MailBuilder {

    private static final Map<Player, Builder> mailDrafts = new HashMap<>();

    public static Builder getMailDraft(Player player) { return mailDrafts.get(player); }

    public final static class Builder {

        private Mail.MailType mailType;
        private Player sender;
        private String message;
        private ItemStack icon;
        private List<OfflinePlayer> recipients;

        public Builder(Mail.MailType type, Player sender) {
            this.sender = sender;
            this.mailType = type;
            this.recipients = new ArrayList<>();
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

        public Mail build() {

            if (!(message != null && icon != null)); //TODO throw error

            mailDrafts.remove(sender);

            switch (mailType) {
                case MAIL_ITEM:
                    return new MailItems(icon);
                case MAIL_MESSAGE:
                    return new MailMessages(icon, message);
            }
            return null;
        }
    }
    //Fields omitted for brevity.
    private MailBuilder() {
        //Constructor is now private.
    }
}
