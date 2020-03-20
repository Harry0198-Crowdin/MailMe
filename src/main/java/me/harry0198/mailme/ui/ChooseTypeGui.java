package me.harry0198.mailme.ui;

import me.harry0198.mailme.MailMe;
import me.harry0198.mailme.datastore.PlayerData;
import me.harry0198.mailme.mail.Mail;
import me.harry0198.mailme.mail.MailBuilder;
import me.harry0198.mailme.utility.Utils;
import me.mattstudios.mfgui.gui.guis.Gui;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class ChooseTypeGui {

    private final MailMe plugin;
    private final Gui ui;

    public ChooseTypeGui(MailMe plugin) {
        this.plugin = plugin;
        this.ui = new Gui(plugin,3, ChatColor.YELLOW + "MailMe");
        ui.fill(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), e -> e.setCancelled(true)));
    }

    public void open(Player player) {
        // So it doesn't alter actual object
        Gui gui = ui;

        PlayerData data = plugin.getPlayerDataHandler().getPlayerData(player);

        gui.setItem(2,3, new GuiItem(Utils.getItemStack(plugin.getLocale().getConfigurationSection(data.getLang(), "gui.choose.message")), event -> {
            event.setCancelled(true);
            new MailBuilder.Builder(Mail.MailType.MAIL_MESSAGE, player);
            plugin.getGuiHandler().getIconGui(player).open();
        }));
        gui.setItem(2,4, new GuiItem(Utils.getItemStack(plugin.getLocale().getConfigurationSection(data.getLang(), "gui.choose.item")), event -> {
            event.setCancelled(true);
            new MailBuilder.Builder(Mail.MailType.MAIL_ITEM, player);
            plugin.getGuiHandler().getIconGui(player).open();
        }));
        gui.setItem(2,6, new GuiItem(Utils.getItemStack(plugin.getLocale().getConfigurationSection(data.getLang(), "gui.choose.sound")), event -> {
            event.setCancelled(true);
            new MailBuilder.Builder(Mail.MailType.MAIL_SOUND, player);
            plugin.getGuiHandler().getIconGui(player).open();
        }));
        gui.setItem(2,7, new GuiItem(Utils.getItemStack(plugin.getLocale().getConfigurationSection(data.getLang(), "gui.choose.location")), event -> {
            event.setCancelled(true);
            new MailBuilder.Builder(Mail.MailType.MAIL_LOCATION, player);
            plugin.getGuiHandler().getIconGui(player).open();
        }));
        gui.open(player);
    }

}
