package me.harry0198.mailme.ui.send;

import me.harry0198.mailme.MailMe;
import me.mattstudios.mfgui.gui.GUI;
import me.mattstudios.mfgui.gui.GuiItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class SendGui {

    private final GUI gui;

    public SendGui(MailMe plugin) {
        gui = new GUI(plugin, 3, "Are you Sure?");
        gui.fill(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), event -> event.setCancelled(true)));
    }

    public GUI getGui(GuiItem item) {
        GUI tmpGui = gui;
        tmpGui.setItem(2,5, item);
        return tmpGui;
    }
}
