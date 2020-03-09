package me.harry0198.mailme.conversations;

import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.entity.Player;

public class ConversationAbandonedListener implements org.bukkit.conversations.ConversationAbandonedListener {

    public void conversationAbandoned(ConversationAbandonedEvent abandonedEvent) {
        Player player = (Player) abandonedEvent.getContext().getForWhom();
        if (!(abandonedEvent.gracefulExit())) {
            //TODO exit msg
        }
    }
}
