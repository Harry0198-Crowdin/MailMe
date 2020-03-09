package me.harry0198.mailme.command;

import me.harry0198.mailme.MailMe;
import me.harry0198.mailme.mail.types.MailMessages;
import me.harry0198.mailme.utility.Utils;
import me.mattstudios.mf.annotations.Alias;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.annotations.SubCommand;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

@Command("mailme")
@Alias("mail")
@SuppressWarnings({"unused"})
public class MailCmd extends CommandBase {

    private MailMe plugin;

    public MailCmd(MailMe plugin) {
        this.plugin = plugin;
    }

    @Default
    public void execute(Player player) {
        String string = new MailMessages(new ItemStack(Material.STONE), "").toString();
        System.out.println(Utils.mailObjFromString(string));
    }

    @SubCommand("read")
    public void execute2(Player player) {
        plugin.getGuiHandler().getPaginatedGui().getGui(Collections.emptyList(), player, 1).open(player);
    }

    @SubCommand("send")
    public void execute3(Player player) {
        plugin.getGuiHandler().getMailGui().getGui().open(player);
    }

}
