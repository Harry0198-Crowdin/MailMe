package me.harry0198.mailme.ui;

import me.harry0198.mailme.MailMe;
import me.harry0198.mailme.mail.MailBuilder;
import me.harry0198.mailme.utility.Utils;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class IconGui extends PaginationGui {

    private final MailMe plugin;

    public IconGui(MailMe plugin, Player player, List<?> items, int page) {
        super(plugin, player, items, page);
        this.plugin = plugin;

        applyNavBut(page);
    }

    @Override
    public void open() {
        ItemStack stack = Utils.getItemStack(plugin.getLocale().getConfigurationSection(getPlayerData().getLang(), "gui.icon-custom"));
        GuiItem gi = new GuiItem(stack, event -> {
            event.setCancelled(true);
            MailBuilder.getMailDraft(getPlayer()).setIcon(event.getCursor());
            plugin.getGuiHandler().getChoosePlayerGui(getPlayer()).open();
        });
        List<GuiItem> iconList = new ArrayList<>();

        getPage(getCurrentPage()).forEach(it -> {
            ItemStack item = (ItemStack) it;
            iconList.add(new GuiItem(item, event -> {
                event.setCancelled(true);
                MailBuilder.getMailDraft(getPlayer()).setIcon(item);
                plugin.getGuiHandler().getChoosePlayerGui(getPlayer()).open();
            }));
        });

        getGui().setItem(3,5,gi);
        addBetweenPoints(iconList);

        getGui().open(getPlayer());
    }

    @Override
    public PaginationGui newInstance(int page) {
        return new IconGui(getPlugin(), getPlayer(), getAllItems(), page);
    }
}
