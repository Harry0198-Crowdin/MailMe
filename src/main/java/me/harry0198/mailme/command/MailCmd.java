package me.harry0198.mailme.command;

import me.harry0198.mailme.MailMe;
import me.mattstudios.mf.annotations.Alias;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.annotations.SubCommand;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.entity.Player;


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
       // new GUI(plugin, 5, "").fillBetweenPoints(2,2,4,8, Collections.singletonList(new GuiItem(new ItemStack(Material.EMERALD)))).open(player);
        player.spigot().sendMessage(plugin.getPlayerDataHandler().getPlayerData(player).getMail().get(0).getMailAsText());
    }

    @SubCommand("read")
    public void execute2(Player player) {
        System.out.println(plugin.getPlayerDataHandler().getPlayerData(player).getMail().get(0).getIcon());
    }

    @SubCommand("send")
    public void execute3(Player player) {
        //plugin.getGuiHandler().getMailGui().getGui().open(player);
    }

}
