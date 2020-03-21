package me.harry0198.mailme.ui;

import me.harry0198.mailme.MailMe;
import me.harry0198.mailme.components.IncompleteBuilderException;
import me.harry0198.mailme.mail.MailBuilder;
import me.mattstudios.mfgui.gui.guis.Gui;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class ItemInputGui {

    private final Gui gui;
    private final Player player;
    private List<ItemStack> items = new ArrayList<>();

    public ItemInputGui(MailMe plugin, Player player) {
        this.gui = new Gui(plugin, 2, "MailMe");
        this.player = player;
        gui.fillBottom(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), event -> event.setCancelled(true)));
        gui.setItem(2,9, new GuiItem(GuiHandler.getContinue(plugin.getLocale(), plugin.getPlayerDataHandler().getPlayerData(player).getLang()), event -> {
            event.setCancelled(true);
            MailBuilder.Builder draft = MailBuilder.getMailDraft(player);
            try {

                for (int i = 0; i < 9; i++) {
                    ItemStack item = event.getInventory().getItem(i);
                    if (item == null)
                        continue;
                    draft.addItem(item);
                }
                draft.build().sendMail();
            } catch (IncompleteBuilderException ibe) {
                ibe.printStackTrace();
            }
        }));

        gui.setCloseGuiAction(event -> {


            for (int i = 0; i < 9; i++) {
                ItemStack item = event.getInventory().getItem(i);
                if (item == null)
                    continue;
                items.add(item);
            }

            ItemStack[] stockArr = new ItemStack[items.size()];
            stockArr = items.toArray(stockArr);
            event.getPlayer().getInventory().addItem(stockArr);
        });
    }

    public void open() {
        gui.open(player);
    }

}
