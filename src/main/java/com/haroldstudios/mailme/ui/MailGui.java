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
import com.haroldstudios.mailme.mail.Mail;
import com.haroldstudios.mailme.utility.Utils;
import me.mattstudios.mfgui.gui.components.ItemBuilder;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class MailGui extends PaginationGui {

    public MailGui(MailMe plugin, Player player, List<?> items, int page) {
        super(plugin, player, items, page, null);
        updateTitlePath(getPlugin().getLocale().getMessage(getPlayerData().getLang(), "gui.read-title"));
        applyNavBut(page);

        if (plugin.getConfig().getBoolean("gui.read.read-as-text-button.enabled")) {
            getGui().setItem(plugin.getConfig().getInt("gui.read.read-as-text-button.slot"), new GuiItem(GuiHandler.getTextButton(getPlugin().getLocale(), getPlayerData().getLang()), event -> {
                event.setCancelled(true);
                getGui().close(player);
                plugin.getCmds().readAsText(player, 0);
                GuiHandler.playUISound(player);
            }));
        }

        if (plugin.getConfig().getBoolean("gui.read.send-button.enabled")) {
            getGui().setItem(plugin.getConfig().getInt("gui.read.send-button.slot"), new GuiItem(GuiHandler.getSend(getPlugin().getLocale(), getPlayerData().getLang()), event -> {
                event.setCancelled(true);
                getPlugin().getCmds().send(player, new String[]{"send"});
                GuiHandler.playUISound(player);
            }));
        }
    }

    @Override
    public void open() {

        if (getPlugin().getConfig().getBoolean("gui.read.set-filter.enabled")) {
            getGui().setItem(getPlugin().getConfig().getInt("gui.read.set-filter.slot"), new GuiItem(GuiHandler.getSearchIcon(getPlugin().getLocale(), getPlayerData().getLang()), event -> {
                event.setCancelled(true);
                getPlugin().getSearchFactory().buildConversation(getPlayer()).begin();
                getGui().close(getPlayer());
                GuiHandler.playUISound(getPlayer());
            }));
        }
        if (getPlugin().getConfig().getBoolean("gui.read.clear-filter.enabled")) {
            getGui().setItem(getPlugin().getConfig().getInt("gui.read.clear-filter.slot"), new GuiItem(removeFilter(), event -> {
                event.setCancelled(true);
                new MailGui(getPlugin(), getPlayer(), getPlayerData().getMail(), 0).open();
                GuiHandler.playUISound(getPlayer());
            }));
        }

        if (getAllItems().size() == 0) {
           setNoResults();
           getGui().open(getPlayer());
           return;
        }



        List<GuiItem> mailList = new ArrayList<>();
        int i = 0;
        for (Object m : super.getPage(getCurrentPage())) {

            // If it's in this class - it will be mail!
            Mail mail = (Mail) m;

            List<String> lore = Utils.applyPlaceHolders(mail, getPlugin().getLocale().getMessages(super.getPlayerData().getLang(), "gui.mail.lore"), getPlayer().getUniqueId());
            String[] stockArr = new String[lore.size()];
            stockArr = lore.toArray(stockArr);

            ItemStack itemStack = new ItemBuilder(mail.getIcon())
                    .setName(Utils.applyPlaceHolders(mail, getPlugin().getLocale().getMessage(super.getPlayerData().getLang(), "gui.mail.title"), getPlayer().getUniqueId()))
                    .setLore(stockArr)
                    .glow(!mail.isRead()).build();


            if (mail.isRead())
                itemStack.removeEnchantment(Enchantment.LURE);


            int finalI = i;
            GuiItem item = new GuiItem(itemStack, event -> {
                event.setCancelled(true);
                if (event.isLeftClick()) {
                    Player player = (Player) event.getWhoClicked();
                    mail.onClick(player);
                    mail.setRead(true, player);
                    newInstance(getCurrentPage()).open();
                    GuiHandler.playDingSound(getPlayer());
                } else {
                    getGui().setItem(finalI + 1, getAreYouSure(mail));
                    getGui().update();
                }
            });
            mailList.add(item);
            i++;
        }

        addBetweenPoints(mailList);
        getGui().open(getPlayer());
    }

    private GuiItem getAreYouSure(Mail mail) {
        return new GuiItem(areYouSure(getPlayerData().getLang()), event1 -> {
            if (event1.isRightClick()) {
                getPlayerData().deleteMail(mail);
            }
            new MailGui(getPlugin(), getPlayer(), getPlayerData().getMail(), getCurrentPage()).open();
        });
    }

    @Override
    public PaginationGui newInstance(int page) {
        return new MailGui(getPlugin(), getPlayer(), getAllItems(), page);
    }


}
