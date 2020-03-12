package me.harry0198.mailme.conversations;

import me.harry0198.mailme.MailMe;
import me.harry0198.mailme.mail.MailBuilder;
import me.harry0198.mailme.utility.Utils;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

public class InputPrompt extends StringPrompt {

    public String getPromptText(ConversationContext context) {
        return "What would you like it to say?";
    }

    @Override
    public Prompt acceptInput(ConversationContext context, String s) {

        if (s.equals("cancel")) {
            return Prompt.END_OF_CONVERSATION;
        }

        Player player = (Player) context.getForWhom();
        MailBuilder.getMailDraft(player).setMessage(s);
        return Prompt.END_OF_CONVERSATION;
    }
}
