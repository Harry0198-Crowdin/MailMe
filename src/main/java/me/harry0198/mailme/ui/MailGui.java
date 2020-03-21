package me.harry0198.mailme.ui;

import me.harry0198.mailme.MailMe;
import me.harry0198.mailme.mail.Mail;
import me.harry0198.mailme.utility.Utils;
import me.mattstudios.mfgui.gui.components.ItemBuilder;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public final class MailGui extends PaginationGui {

    public MailGui(MailMe plugin, Player player, List<?> items, int page) {
        super(plugin, player, items, page);
        updateTitlePath(getPlugin().getLocale().getMessage(getPlayerData().getLang(), "gui.read-title"));
        applyNavBut(page);

        getGui().setItem(3,1,new GuiItem(GuiHandler.getTextButton(getPlugin().getLocale(), getPlayerData().getLang()), event -> {
            event.setCancelled(true);
            getGui().close(player);
            plugin.getCmds().readAsText(player, 0);
        }));
    }

    @Override
    public void open() {

        getGui().setItem(3,9,new GuiItem(GuiHandler.getSearchIcon(getPlugin().getLocale(), getPlayerData().getLang()), event -> {
            event.setCancelled(true);
            getPlugin().getSearchFactory().buildConversation(getPlayer()).begin();
            getGui().close(getPlayer());
        }));

        getGui().setItem(4,9, new GuiItem(removeFilter(), event -> {
            event.setCancelled(true);
            new MailGui(getPlugin(), getPlayer(), getPlayerData().getMail(), 0).open();
        }));

        if (getAllItems().size() == 0) {
           setNoResults();
           getGui().open(getPlayer());
           return;
        }

        List<GuiItem> mailList = new ArrayList<>();

        for (Object m : super.getPage(getCurrentPage())) {
            // If it's in this class - it will be mail!
            Mail mail = (Mail) m;

            List<String> lore = Utils.applyPlaceHolders(mail, getPlugin().getLocale().getMessages(super.getPlayerData().getLang(), "gui.mail.lore"));
            String[] stockArr = new String[lore.size()];
            stockArr = lore.toArray(stockArr);

            ItemBuilder itemBuilder = new ItemBuilder(mail.getIcon())
                    .setName(Utils.applyPlaceHolders(mail, getPlugin().getLocale().getMessage(super.getPlayerData().getLang(), "gui.mail.title")))
                    .setLore(stockArr);

            if (!mail.isRead())
                itemBuilder.glow();
            mailList.add(new GuiItem(itemBuilder.build(), event -> {
                event.setCancelled(true);
                mail.getMail().open(getPlayer());
            }));
        }

        addBetweenPoints(mailList);
        getGui().open(getPlayer());
    }

    @Override
    public PaginationGui newInstance(int page) {
        return new MailGui(getPlugin(), getPlayer(), getAllItems(), page);
    }


}
