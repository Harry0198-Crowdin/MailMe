package me.harry0198.mailme.ui;

import me.harry0198.mailme.MailMe;
import me.harry0198.mailme.components.IncompleteBuilderException;
import me.harry0198.mailme.mail.MailBuilder;
import me.mattstudios.mfgui.gui.components.ItemBuilder;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ChoosePlayerGui extends PaginationGui {

    public ChoosePlayerGui(MailMe plugin, Player player, List<?> items, int currentPage) {
        super(plugin, player, items, currentPage);

        applyNavBut(currentPage);
        getGui().setItem(2,2, GuiHandler.getLoading(getPlugin().getLocale(), getPlayerData().getLang()));
        updateTitlePath(getPlugin().getLocale().getMessage(getPlayerData().getLang(), "gui.choose-player-title"));

        getGui().setItem(3,9,new GuiItem(GuiHandler.getSearchIcon(getPlugin().getLocale(), getPlayerData().getLang()), event -> {
            event.setCancelled(true);
            getPlugin().getSearchPlayerFactory().buildConversation(getPlayer()).begin();
            getGui().close(getPlayer());
        }));

        getGui().setItem(4,9, new GuiItem(removeFilter(), event -> {
            event.setCancelled(true);
            getPlugin().getGuiHandler().getChoosePlayerGui(getPlayer()).open();
        }));

        getGui().setItem(5,9,new GuiItem(GuiHandler.getContinue(getPlugin().getLocale(), getPlayerData().getLang()), event -> {
            event.setCancelled(true);
            if (!MailBuilder.getMailDraft(getPlayer()).getRecipients().isEmpty())
                openSpecificInputGui();
        }));
    }

    @Override
    public void open() {

        getGui().open(getPlayer());

         Bukkit.getScheduler().runTaskAsynchronously(getPlugin(), () -> {
            List<GuiItem> heads = new ArrayList<>();
            MailBuilder.Builder draft = MailBuilder.getMailDraft(getPlayer());
            int slot = 1;
            for (Object each : getPage(getCurrentPage())) {
                OfflinePlayer p = (OfflinePlayer) each;
                if (p.hasPlayedBefore() || p.isOnline()) {
                    ItemBuilder builder = new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(p).setName(ChatColor.AQUA + p.getName());

                    if (draft.getRecipients().contains(p)) {
                        getGui().setItem(slot, new GuiItem(new ItemBuilder(Material.EMERALD).glow().build(), e -> e.setCancelled(true)));
                    }
                    int finalSlot = slot;
                    heads.add(new GuiItem(builder.build(), event -> {
                        event.setCancelled(true);
                        if (draft.getRecipients().contains(p)) {
                            draft.removeRecipient(getPlayer());
                            getGui().setItem(finalSlot, new GuiItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build(), e -> e.setCancelled(true)));
                        } else {
                            draft.addRecipient(getPlayer());
                            getGui().setItem(finalSlot, new GuiItem(new ItemBuilder(Material.EMERALD).glow().build(), e -> e.setCancelled(true)));
                        }
                        getGui().update();

                    }));
                    slot++;
                } else
                    setNoResults();
            }

            if (getAllItems().size() == 0) {
                setNoResults();
                getGui().open(getPlayer());
                return;
            }

            addBetweenPoints(heads);
            Bukkit.getScheduler().runTask(getPlugin(), () -> getGui().open(getPlayer()));
        });
    }

    public void openSpecificInputGui() {
        MailBuilder.Builder builder = MailBuilder.getMailDraft(getPlayer());
        try {
            switch (builder.getMailType()) {

                case MAIL_MESSAGE:
                    getPlayer().closeInventory();
                    getPlugin().getConversationFactory().buildConversation(getPlayer()).begin();
                    break;
                case MAIL_ITEM:
                    getPlugin().getGuiHandler().getItemInputGui(getPlayer()).open();
                    break;
                case MAIL_SOUND:
                    getPlugin().getGuiHandler();
                    break;
                case MAIL_LOCATION:
                    getPlayer().closeInventory();
                    builder.setLocation(getPlayer().getLocation());
                    Objects.requireNonNull(builder.build()).sendMail();
                    break;
            }
        } catch (IncompleteBuilderException ibe) { // Shouldn't EVER hit here
            ibe.printStackTrace();
        }
    }

    @Override
    public PaginationGui newInstance(int page) {
        return new ChoosePlayerGui(getPlugin(), getPlayer(), new ArrayList<>(Bukkit.getOnlinePlayers()), page);
    }
}
