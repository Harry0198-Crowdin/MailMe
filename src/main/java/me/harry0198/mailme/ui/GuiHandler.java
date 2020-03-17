package me.harry0198.mailme.ui;

import me.harry0198.mailme.MailMe;

import me.mattstudios.mfgui.gui.guis.GuiItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GuiHandler {

    private PaginatedGui paginatedGui;

    public GuiHandler(MailMe plugin) {
        List<GuiItem> items = new ArrayList<>();
        for (int i = 0; i < 62; i++) {
            items.add(new GuiItem(new ItemStack(Material.CYAN_BANNER)));
        }
        //paginatedGui = new PaginatedGui(30, items);
    }

    public PaginatedGui getPaginatedGui() { return paginatedGui; }

}
