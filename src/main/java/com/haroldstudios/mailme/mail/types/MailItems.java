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

package com.haroldstudios.mailme.mail.types;

import com.haroldstudios.mailme.mail.Mail;
import com.haroldstudios.mailme.utility.NMSReflection;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.*;

@SuppressWarnings({"unused"})
public final class MailItems extends Mail implements Serializable {

    private List<ItemStack> items;

    /**
     *
     * @param recipients List of OfflinePlayers
     * @param stacks List of ItemStacks
     */
    public MailItems(List<OfflinePlayer> recipients, List<ItemStack> stacks) {
        this(new ItemStack(Material.STONE), recipients, stacks);
    }

    /**
     *
     * @param icon Icon ItemStack
     * @param recipients List of OfflinePlayers
     * @param stacks List of ItemStacks
     */
    public MailItems(ItemStack icon, List<OfflinePlayer> recipients, List<ItemStack> stacks) {
        this(icon,new Date(),recipients,stacks,null);
    }

    /**
     *
     * @param icon Icon ItemStack
     * @param date Date to stamp onto mail
     * @param recipients List of OfflinePlayers
     * @param stacks List of ItemStacks
     */
    public MailItems(ItemStack icon, Date date, List<OfflinePlayer> recipients, List<ItemStack> stacks) {
        this(icon, date, recipients, stacks, null);
    }

    /**
     *
     * @param icon Icon ItemStack
     * @param recipients List of OfflinePlayers
     * @param stacks List of ItemStack mail
     * @param sender Sender of Mail
     */
    public MailItems(ItemStack icon, List<OfflinePlayer> recipients, List<ItemStack> stacks, UUID sender) {
        this(icon, new Date(), recipients, stacks, sender);
    }


    /**
     *
     * @param icon Icon ItemStack
     * @param date Date to stamp onto mail
     * @param recipients List of OfflinePlayers
     * @param stacks List of ItemStack mail
     * @param sender Sender of Mail
     */
    public MailItems(ItemStack icon, Date date, List<OfflinePlayer> recipients, List<ItemStack> stacks, UUID sender) {
        super(icon, date);
        super.setSender(sender);
        items = stacks;
        if (!recipients.isEmpty()) // Prevents duplicating items
            super.addRecipients(Collections.singletonList(recipients.get(0)));
    }

    /**
     * Gets all items in Mail
     *
     * @return List of ItemStack
     */
    public List<ItemStack> getItems() {
        return items;
    }

    @Override
    public void onClick(Player player) {
        if (!isRead()) {
            items.forEach(item -> player.getInventory().addItem(item));
        }
    }


    @Override
    public MailType getMailType() {
        return MailType.MAIL_ITEM;
    }

    @Override
    public BaseComponent[] getContentsAsText() {
        ComponentBuilder builder = new ComponentBuilder("");
        for (ItemStack item : getItems()) {
            TextComponent txt = new TextComponent(item.getAmount() + " * " + item.getType() + " ");
            txt.setColor(ChatColor.AQUA);
            txt.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new ComponentBuilder(NMSReflection.convertItemStackToJson(item)).create()));
            builder.append(txt);
        }
        return builder.create();
    }
}
