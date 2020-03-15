package me.harry0198.mailme.conversations;

import org.bukkit.conversations.ConversationContext;

public class ConversationPrefix implements org.bukkit.conversations.ConversationPrefix {

    public String getPrefix(ConversationContext context) {
        return "§8[§e§lMailMe§8]§r";
    }
}
