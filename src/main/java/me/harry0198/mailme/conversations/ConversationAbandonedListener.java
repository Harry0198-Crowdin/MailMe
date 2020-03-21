package me.harry0198.mailme.conversations;

import me.harry0198.mailme.mail.Mail;
import org.bukkit.conversations.ConversationAbandonedEvent;

public class ConversationAbandonedListener implements org.bukkit.conversations.ConversationAbandonedListener {

    @Override
    public void conversationAbandoned(ConversationAbandonedEvent abandonedEvent) {
        Mail mail = (Mail) abandonedEvent.getContext().getSessionData("mail");
        mail.sendMail();
    }
}
