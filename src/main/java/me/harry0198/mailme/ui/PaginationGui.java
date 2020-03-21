package me.harry0198.mailme.ui;

import me.harry0198.mailme.MailMe;
import me.harry0198.mailme.datastore.PlayerData;
import me.harry0198.mailme.utility.Pagination;
import me.harry0198.mailme.utility.Utils;
import me.mattstudios.mfgui.gui.guis.Gui;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

abstract class PaginationGui {

    private final MailMe plugin;
    private final List<Object> allItems = new ArrayList<>();
    private final PlayerData data;
    private final Player player;
    private final Pagination<?> pages;
    private final int rows = 5;
    private final Gui gui;
    private final int currentPage;

    PaginationGui(MailMe plugin, Player player, List<?> items, int currentPage) {
        this.plugin = plugin;
        this.player = player;
        this.data = plugin.getPlayerDataHandler().getPlayerData(player);
        this.pages = new Pagination<>(7, items);
        this.gui = new Gui(plugin, rows, "MailMe");
        this.allItems.addAll(items);
        this.currentPage = currentPage;

        gui.fill(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), e -> e.setCancelled(true)));
        gui.setItem(5,5,new GuiItem(Utils.getItemStack(plugin.getLocale().getConfigurationSection(data.getLang(), "gui.exit-button")), e -> {
            e.setCancelled(true);
            gui.close(player);
        }));
    }

    protected void applyNavBut(int page) {
        if ((page - 1) >= 0)
            gui.setItem(2,1, new GuiItem(GuiHandler.getPrevious(plugin.getLocale(), data.getLang()), event -> {
                event.setCancelled(true);
                newInstance(page - 1).open();
            }));

        if ((page + 1) < pages.totalPages())
            gui.setItem(2,9,new GuiItem(GuiHandler.getNext(plugin.getLocale(), data.getLang()), event -> {
                event.setCancelled(true);
                newInstance(page + 1).open();

            }));
    }


    protected void setNoResults() {
        gui.setItem(2,5, new GuiItem(GuiHandler.getNoResults(getPlugin().getLocale(), data.getLang()), e -> e.setCancelled(true)));
    }

    protected List<?> getPage(int page) {
        return pages.getPage(page);
    }

    protected MailMe getPlugin() {
        return plugin;
    }

    protected PlayerData getPlayerData() {
        return data;
    }

    protected List<?> getAllItems() {
        return allItems;
    }

    protected Player getPlayer() {
        return player;
    }

    protected ItemStack removeFilter() {
        return Utils.getItemStack(plugin.getLocale().getConfigurationSection(data.getLang(), "gui.remove-filter"));
    }

    protected void addBetweenPoints(List<GuiItem> guiItems) {
        Collections.reverse(guiItems);
        int marker = 0;

        for (int row = 1; row <= rows; row++) {
            for (int col = 1; col <= 9; col++) {
                int slot = getSlotFromRowCol(row, col);
                if (!((row == 2) && (col >= 2 && col <= 8)))
                    continue;
                if (marker >= guiItems.size())
                    break;
                gui.setItem(slot, guiItems.get(marker));
                marker++;
            }
        }
    }

    protected void updateTitlePath(String addition) {
        gui.updateTitle("MailMe // " + addition);
    }

    public Gui getGui() {
        return gui;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    private int getSlotFromRowCol(final int row, final int col) {
        return (col + (row - 1) * 9) - 1;
    }


    public abstract void open();
    public abstract PaginationGui newInstance(int page);
}
