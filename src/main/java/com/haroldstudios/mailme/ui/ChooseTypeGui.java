/*
 *   Copyright [2020] [Harry0198]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.haroldstudios.mailme.ui;

import com.haroldstudios.mailme.MailMe;
import com.haroldstudios.mailme.datastore.PlayerData;
import com.haroldstudios.mailme.mail.Mail;
import com.haroldstudios.mailme.utility.Utils;
import com.haroldstudios.mailme.mail.MailBuilder;
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
        ui.getFiller().fill(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), e -> e.setCancelled(true)));
    }

    public void open(Player player) {
        create(player);
        // So it doesn't alter actual object
        Gui gui = ui;

        PlayerData data = plugin.getDataStoreHandler().getPlayerData(player);

        gui.setItem(2,3, new GuiItem(Utils.getItemStack(plugin.getLocale().getConfigurationSection(data.getLang(), "gui.choose.message")), event -> {
            event.setCancelled(true);
            MailBuilder.getMailDraft(player).setMailType(Mail.MailType.MAIL_MESSAGE);
            plugin.getGuiHandler().getIconGui(player).open();
            GuiHandler.playUISound(player);
        }));
        gui.setItem(2,4, new GuiItem(Utils.getItemStack(plugin.getLocale().getConfigurationSection(data.getLang(), "gui.choose.item")), event -> {
            event.setCancelled(true);
            MailBuilder.getMailDraft(player).setMailType(Mail.MailType.MAIL_ITEM);
            plugin.getGuiHandler().getIconGui(player).open();
            GuiHandler.playUISound(player);
        }));
        gui.setItem(2,6, new GuiItem(Utils.getItemStack(plugin.getLocale().getConfigurationSection(data.getLang(), "gui.choose.sound")), event -> {
            event.setCancelled(true);
            MailBuilder.getMailDraft(player).setMailType(Mail.MailType.MAIL_SOUND);
            plugin.getGuiHandler().getIconGui(player).open();
            GuiHandler.playUISound(player);
        }));
        gui.setItem(2,7, new GuiItem(Utils.getItemStack(plugin.getLocale().getConfigurationSection(data.getLang(), "gui.choose.location")), event -> {
            event.setCancelled(true);
            MailBuilder.getMailDraft(player).setMailType(Mail.MailType.MAIL_LOCATION);
            plugin.getGuiHandler().getIconGui(player).open();
            GuiHandler.playUISound(player);
        }));
        gui.open(player);
    }

    public void create(Player player) {
        if (MailBuilder.getMailDraft(player) == null)
            new MailBuilder.Builder(Mail.MailType.MAIL_MESSAGE, player);
    }

}
