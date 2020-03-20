package me.harry0198.mailme.ui;

import me.harry0198.mailme.MailMe;

import me.harry0198.mailme.utility.Locale;
import me.harry0198.mailme.utility.Utils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class GuiHandler {

    private final MailMe plugin;
    private final ChooseTypeGui chooseTypeGui;
    List<ItemStack> icons = new ArrayList<>();

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

    public ChooseTypeGui getChooseTypeGui() {
        return chooseTypeGui;
    }
}
