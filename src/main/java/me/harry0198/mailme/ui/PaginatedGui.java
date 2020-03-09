package me.harry0198.mailme.ui;

import me.harry0198.mailme.MailMe;
import me.mattstudios.mfgui.gui.GUI;
import me.mattstudios.mfgui.gui.GuiItem;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaginatedGui {

    private GUI gui;
    private static Map<Player, Integer> page = new HashMap<>();
    private final static int SLOT_SIZE = 7;

    public PaginatedGui(final MailMe plugin) {
        gui = new GUI(plugin, 5, "");
    }

    public GUI getGui(final List<GuiItem> items, final Player player, int page) {
        if (page == 0) page = 1;
        PaginatedGui.page.putIfAbsent(player, page);
        GUI tmpGui = gui;

        int neededInvs = ((int) Math.ceil(items.size() / (double) SLOT_SIZE));

        int index = (page * SLOT_SIZE) - 1; // Array starts at 0
        if (index > items.size()) return tmpGui;

        int startPos = 10; // Where to put the GuiItems
        for (int i = index; i <= index + SLOT_SIZE; i++) {
            if (items.size() < i + 1) return tmpGui;
            tmpGui.setItem(startPos, items.get(i));
        }
        return tmpGui;
    }
}
