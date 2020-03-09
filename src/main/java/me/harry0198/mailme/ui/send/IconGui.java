package me.harry0198.mailme.ui.send;

import me.harry0198.mailme.MailMe;
import me.harry0198.mailme.mail.MailBuilder;
import me.mattstudios.mfgui.gui.GUI;
import me.mattstudios.mfgui.gui.GuiItem;
import org.bukkit.Material;
import org.bukkit.conversations.Conversable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class IconGui {

    private final GUI gui;

    public IconGui(MailMe plugin) {
        this.gui = new GUI(plugin,5,"Choose an Icon!");
        ItemStack stack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        gui.setItem(1,1, new GuiItem(stack, event -> {
            event.setCancelled(true);
            MailBuilder.getMailDraft((Player) event.getWhoClicked()).setIcon(stack);
            event.getWhoClicked().getOpenInventory().close();
            //TODO title -> write your message!
            plugin.getConversationFactory().buildConversation((Conversable) event.getWhoClicked()).begin();
        }));
    }

    public GUI getGui() {
        return gui;
    }
}
