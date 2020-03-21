package me.harry0198.mailme.ui;

import me.harry0198.mailme.MailMe;

import me.harry0198.mailme.utility.Locale;
import me.harry0198.mailme.utility.Utils;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class GuiHandler {

    private final MailMe plugin;
    private final ChooseTypeGui chooseTypeGui;
    private List<ItemStack> icons = new ArrayList<>();

    public GuiHandler(MailMe plugin) {
        this.plugin = plugin;
        this.chooseTypeGui = new ChooseTypeGui(plugin);

        ConfigurationSection section = plugin.getConfig().getConfigurationSection("icons");
        for (String key : section.getKeys(false)) {
            icons.add(section.getItemStack(key));
        }
    }

    public IconGui getIconGui(Player player) {
        return new IconGui(plugin, player, icons, 0);
    }

    public ChoosePlayerGui getChoosePlayerGui(Player player) {
        return new ChoosePlayerGui(plugin, player, new ArrayList<>(Bukkit.getOnlinePlayers()), 0);
    }

    public static ItemStack getPrevious(Locale locale, Locale.LANG lang) {
        return Utils.getItemStack(locale.getConfigurationSection(lang, "gui.back-button"));
    }

    public static ItemStack getNext(Locale locale, Locale.LANG lang) {
        return Utils.getItemStack(locale.getConfigurationSection(lang, "gui.forward-button"));
    }

    public static ItemStack getSearchIcon(Locale locale, Locale.LANG lang) {
        return Utils.getItemStack(locale.getConfigurationSection(lang, "gui.search-button"));
    }

    public static ItemStack getNoResults(Locale locale, Locale.LANG lang) {
        return Utils.getItemStack(locale.getConfigurationSection(lang, "gui.no-results"));
    }

    public static GuiItem getLoading(Locale locale, Locale.LANG lang) {
        return new GuiItem(Utils.getItemStack(locale.getConfigurationSection(lang, "gui.loading")), event -> event.setCancelled(true));
    }

    public static ItemStack getTextButton(Locale locale, Locale.LANG lang) {
        return Utils.getItemStack(locale.getConfigurationSection(lang, "gui.text-button"));
    }

    public static ItemStack getContinue(Locale locale, Locale.LANG lang) {
        return Utils.getItemStack(locale.getConfigurationSection(lang, "gui.continue"));
    }


    public ChooseTypeGui getChooseTypeGui() {
        return chooseTypeGui;
    }

    public ItemInputGui getItemInputGui(Player player) {
        return new ItemInputGui(plugin, player);
    }
}
