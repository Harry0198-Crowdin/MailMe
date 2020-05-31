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
import com.haroldstudios.mailme.utility.Constants;
import com.haroldstudios.mailme.utility.Utils;
import com.haroldstudios.mailme.mail.MailBuilder;
import me.mattstudios.mfgui.gui.components.ItemBuilder;
import me.mattstudios.mfgui.gui.guis.Gui;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import org.bukkit.entity.Player;

public final class ChooseTypeGui {

    private final MailMe plugin;
    private final Gui ui;

    public ChooseTypeGui(MailMe plugin) {
        this.plugin = plugin;
        this.ui = new Gui(plugin, 3, plugin.getLocale().getMessage("gui.choose-type-title"));
        ui.getFiller().fill(new GuiItem(Utils.getItemStack(plugin.getLocale().getConfigurationSection(plugin.getLocale().getServerLang(), "gui.filler")), e -> e.setCancelled(true)));
        ui.setDefaultClickAction(event -> event.setCancelled(true));
    }

    public void open(Player player, MailBuilder builder) {

        Gui gui = ui;

        PlayerData data = plugin.getDataStoreHandler().getPlayerData(player);
        builder.setSender(player.getUniqueId());

        gui.setDefaultClickAction(event -> event.setCancelled(true));

        // Sets builder types dependent on gui choice & opens next menu
        if (player.hasPermission(Constants.TYPE_PERM + "message") && plugin.getConfig().getBoolean("gui.send.message-mail.enabled")) {
            gui.setItem(plugin.getConfig().getInt("gui.send.message-mail.slot"), new GuiItem(Utils.getItemStack(plugin.getLocale().getConfigurationSection(data.getLang(), "gui.choose.message")), event -> {
                plugin.getGuiHandler().getIconGui(player, builder.setMailType(Mail.MailType.MAIL_MESSAGE)).open();
                GuiHandler.playUISound(player);
            }));
        }
        if (player.hasPermission(Constants.TYPE_PERM + "item") && plugin.getConfig().getBoolean("gui.send.item-mail.enabled")) {
            gui.setItem(plugin.getConfig().getInt("gui.send.item-mail.slot"), new GuiItem(Utils.getItemStack(plugin.getLocale().getConfigurationSection(data.getLang(), "gui.choose.item")), event -> {
                plugin.getGuiHandler().getIconGui(player, builder.setMailType(Mail.MailType.MAIL_ITEM)).open();
                GuiHandler.playUISound(player);
            }));
        }
        if (player.hasPermission(Constants.TYPE_PERM + "sound") && plugin.getConfig().getBoolean("gui.send.sound-mail.enabled")) {
            gui.setItem(plugin.getConfig().getInt("gui.send.sound-mail.slot"), new GuiItem(Utils.getItemStack(plugin.getLocale().getConfigurationSection(data.getLang(), "gui.choose.sound")), event -> {
                plugin.getGuiHandler().getIconGui(player, builder.setMailType(Mail.MailType.MAIL_SOUND)).open();
                GuiHandler.playUISound(player);
            }));
        }
        if (player.hasPermission(Constants.TYPE_PERM + "location") && plugin.getConfig().getBoolean("gui.send.location-mail.enabled")) {
            gui.setItem(plugin.getConfig().getInt("gui.send.location-mail.slot"), new GuiItem(Utils.getItemStack(plugin.getLocale().getConfigurationSection(data.getLang(), "gui.choose.location")), event -> {
                plugin.getGuiHandler().getIconGui(player, builder.setMailType(Mail.MailType.MAIL_LOCATION)).open();
                GuiHandler.playUISound(player);
            }));
        }
        if (player.hasPermission( "mailme.base.send-anonymously") && plugin.getConfig().getBoolean("gui.send.anonymous.enabled")) {
            int anonymousSlot = plugin.getConfig().getInt("gui.send.anonymous.slot");
            gui.addSlotAction(anonymousSlot, event -> {
                GuiHandler.playUISound(player);

                builder.setAnonymous(!builder.isAnonymous());
                gui.updateItem(anonymousSlot, new GuiItem(new ItemBuilder(Utils.getItemStack(plugin.getLocale().getConfigurationSection(data.getLang(), "gui.choose.send-anonymously"))).glow(builder.isAnonymous()).build()));
            });
            gui.setItem(anonymousSlot, new GuiItem(new ItemBuilder(Utils.getItemStack(plugin.getLocale().getConfigurationSection(data.getLang(), "gui.choose.send-anonymously"))).glow(builder.isAnonymous()).build()));
        }
        gui.open(player);

    }

}
