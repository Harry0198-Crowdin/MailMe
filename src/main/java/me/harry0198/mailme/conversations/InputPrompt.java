package me.harry0198.mailme.conversations;

import me.harry0198.mailme.MailMe;
import me.harry0198.mailme.mail.MailBuilder;
import me.mattstudios.mfgui.gui.GuiItem;
import org.bukkit.Material;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
        MailMe.getInstance().getGuiHandler().getSendGui().getGui(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE))).open(player);
        return Prompt.END_OF_CONVERSATION;
    }
}
