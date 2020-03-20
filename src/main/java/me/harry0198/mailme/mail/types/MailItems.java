package me.harry0198.mailme.mail.types;

import me.harry0198.mailme.mail.Mail;
import me.harry0198.mailme.utility.NMSReflection;
import me.mattstudios.mfgui.gui.guis.Gui;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public final class MailItems extends Mail implements Serializable {

    @SuppressWarnings({"unused"})
    private String type = "MailItems"; // For deserializer
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
        if (!recipients.isEmpty()) super.addRecipients(recipients);
    }

    public List<ItemStack> getItems() {
        return items;
    }


    @Override
    public MailType getMailType() {
        return MailType.MAIL_ITEM;
    }

    @Override
    public Gui getMail() {
return null;
    }

    @Override
    public BaseComponent[] getContentsAsText() {
        ComponentBuilder builder = new ComponentBuilder("");
        for (ItemStack item : getItems()) {
            TextComponent txt = new TextComponent(item.getAmount() + " * " + item.getType());
            txt.setColor(ChatColor.AQUA);
            txt.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new ComponentBuilder(NMSReflection.convertItemStackToJson(item)).create()));
            builder.append(txt);
        }
        Bukkit.getPlayer(getSender()).spigot().sendMessage(builder.create());
        return builder.create();
    }
}
