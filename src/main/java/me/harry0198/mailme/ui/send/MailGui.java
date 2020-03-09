package me.harry0198.mailme.ui.send;

import me.harry0198.mailme.MailMe;
import me.harry0198.mailme.mail.Mail;
import me.harry0198.mailme.mail.MailBuilder;
import me.mattstudios.mfgui.gui.GUI;
import me.mattstudios.mfgui.gui.GuiItem;
import me.mattstudios.mfgui.gui.components.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class MailGui {

    private final GUI gui;
    private MailMe plugin;

    public MailGui(MailMe plugin) {
        this.plugin = plugin;
        gui = new GUI(plugin, 3, "Choose Type to Send");
        gui.fill(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), e -> e.setCancelled(true)));
        gui.setItem(2,3, getMessageItem());
        gui.setItem(2,4,getMessageItem());
        gui.setItem(2,5, getMessageItem());
        gui.setItem(2,6, getMessageItem());
        gui.setItem(2,7,getMessageItem());
    }

    public GUI getGui() {
        return gui;
    }

    private GuiItem getMessageItem() {
        ItemStack stack = new ItemBuilder(Material.PAPER).glow().build();
        return new GuiItem(stack, event -> {
            //TODO async
            event.setCancelled(true);
            if (!(event.getWhoClicked() instanceof Player)) return;
            new MailBuilder.Builder(Mail.MailType.MAIL_MESSAGE, (Player) event.getWhoClicked());
            plugin.getGuiHandler().getIconGui().getGui().open(event.getWhoClicked());
        });
    }
}
