package me.harry0198.mailme.conversations;

import me.harry0198.mailme.MailMe;
import me.harry0198.mailme.components.IncompleteBuilderException;
import me.harry0198.mailme.datastore.PlayerData;
import me.harry0198.mailme.mail.Mail;
import me.harry0198.mailme.mail.MailBuilder;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

public class InputPrompt extends StringPrompt {

    public String getPromptText(ConversationContext context) {
        PlayerData data = MailMe.getInstance().getPlayerDataHandler().getPlayerData((Player) context.getForWhom());
        return MailMe.getInstance().getLocale().getMessage(data.getLang(), "mail.message");
    }

    @Override
    public Prompt acceptInput(ConversationContext context, String s) {

        if (s.equals("cancel")) {
            return Prompt.END_OF_CONVERSATION;
        }

        Player player = (Player) context.getForWhom();
        try {
            Mail mail = MailBuilder.getMailDraft(player).setMessage(s).build();
            assert mail != null;
            context.setSessionData("mail", mail);
        } catch (IncompleteBuilderException ibe) {
            ibe.printStackTrace();
        }
        return Prompt.END_OF_CONVERSATION;
    }
}
